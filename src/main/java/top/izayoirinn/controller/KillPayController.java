package top.izayoirinn.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.izayoirinn.common.JsonResult;
import top.izayoirinn.common.KillData;
import top.izayoirinn.config.RabbitMQConfig;
import top.izayoirinn.domain.KillOrderInfo;
import top.izayoirinn.domain.OrderInfo;
import top.izayoirinn.domain.UserInfo;
import top.izayoirinn.exception.GlobalException;
import top.izayoirinn.rabbit.OrderMessage;
import top.izayoirinn.service.KillGoodsService;
import top.izayoirinn.service.KillOrderInfoService;
import top.izayoirinn.service.OrderInfoService;
import top.izayoirinn.service.UserInfoService;
import top.izayoirinn.utils.JsonUtils;
import top.izayoirinn.utils.RedisOperator;
import top.izayoirinn.vo.KillGoodsVO;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Rinn Izayoi
 * @date 2021/7/18 16:55
 * 支付的逻辑处理
 */
@Slf4j
@Controller
@RequestMapping("/seckill")
public class KillPayController {
    @Autowired
    private RedisOperator redisOperator;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private KillOrderInfoService killOrderInfoService;

    @Autowired
    private KillGoodsService killGoodsService;

    /**
     * 提交下单请求
     *
     * @param killId  秒杀商品id
     * @param session 作用域,获取用户登录状态
     * @return 订单信息
     */
    @PostMapping("/kill/{killId}")
    @ResponseBody
    public JsonResult<OrderInfo> killGoods(@PathVariable("killId") Integer killId, HttpSession session) {
        // 参数校验
        if (killId == null || killId < 1) {
            return JsonResult.errorMsg("请选择秒杀商品");
        }
        String stockCountRedisStr = redisOperator.hGet(KillData.KILL_STOCK, String.valueOf(killId));
        if (StringUtils.isBlank(stockCountRedisStr)
                || Integer.parseInt(stockCountRedisStr) <= 0) {
            return JsonResult.errorMsg("商品库存不够...");
        }
        // 判断秒杀商品是否存在
        KillGoodsVO killGoodsVO;
        // 获取存放在redis中商品数据
        String killGoodsRedis = redisOperator.get(KillData.KILL_GOODS_DATA + ":" + killId);
        // null是手动放置的数据(三分钟)，表示数据库并没有此数据信息
        if ("null".equals(killGoodsRedis)) {
            return JsonResult.errorMsg("商品不存在");
        }
        // 正常判断
        if (StringUtils.isNotBlank(killGoodsRedis)) {
            killGoodsVO = JsonUtils.jsonToPojo(killGoodsRedis, KillGoodsVO.class);
        } else {
            synchronized (this) {
                killGoodsRedis = redisOperator.get(KillData.KILL_GOODS_DATA + ":" + killId);
                // null是手动放置的数据(三分钟)，表示数据库并没有此数据信息
                if ("null".equals(killGoodsRedis)) {
                    return JsonResult.errorMsg("商品不存在");
                }
                if (StringUtils.isNotBlank(killGoodsRedis)) {
                    killGoodsVO = JsonUtils.jsonToPojo(killGoodsRedis, KillGoodsVO.class);
                } else {
                    killGoodsVO = killGoodsService.getKillGoodsByKillId(killId);
                    if (killGoodsVO != null) {
                        // 将商品基本信息存储到redis中
                        redisOperator.set(KillData.KILL_GOODS_DATA + ":" + killId,
                                JsonUtils.objectToJson(killGoodsVO), 60 * 10);
                    } else {
                        redisOperator.set(KillData.KILL_GOODS_DATA + ":" + killId,
                                "null", 60 * 3);
                    }
                }
            }
        }
        if (killGoodsVO == null) {
            return JsonResult.errorMsg("商品不存在");
        }
        // 商品秒杀时间判断
        Date startTime = killGoodsVO.getStartDate();
        Date endTime = killGoodsVO.getEndDate();
        Date nowTime = new Date();
        // 如果当前时间比开始时间还早，则秒杀还未开始
        if (nowTime.before(startTime)) {
            return JsonResult.errorMsg("秒杀尚未开始");
        }
        // 如果当前时间比结束时间晚，则秒杀已经结束
        if (nowTime.after(endTime)) {
            return JsonResult.errorMsg("秒杀已经结束");
        }

        // 判断该用户是否登录(拦截器判断),是否已经购买过此商品
        String userName = (String) session.getAttribute("user");

        UserInfo userInfo = userInfoService.getUserByName(userName);
        if (userInfo == null) { // 拦截器应该已经对session中的userName做了判断处理
            return JsonResult.errorMsg("登录信息错误");
        }

        // 使用redis存储订单信息,从redis中获取信息,而不是从订单表中获取
        Boolean member = redisOperator.isMember(
                KillData.KILL_ORDER_USER + ":" + userInfo.getUserId(),
                killId + "");
        if (member) {
            return JsonResult.errorMsg("您已经秒杀过此商品");
        }
        // 从redis中扣减一件商品,数据库库存不做改变
        // 指定 lua 脚本，并且指定返回值类型
        DefaultRedisScript<Long> redisScript =
                new DefaultRedisScript<>(KillData.STOCK_KILL_LUA_SCRIPT, Long.class);
        // 参数一：redisScript，参数二：key列表，参数三：arg（可多个）
        List<String> keyList = new ArrayList<>();
        keyList.add(KillData.KILL_STOCK);
        Long result = redisTemplate.execute(redisScript, keyList, String.valueOf(killId), "-1");
        // result : 返回的值(在这里是剩余的库存数)|| null代表商品为零或者没有此商品
        log.info("redis减少库存,lua脚本执行结果: {} (null表示库存小于等于零)", result);
        if (result == null) {
            return JsonResult.errorMsg("商品库存不够...");
        }

        // 封装消息
        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setUserId(userInfo.getUserId());
        orderMessage.setKillId(killId);

        // 发送消息
        rabbitTemplate.convertAndSend(RabbitMQConfig.KILL_DIRECT_EXCHANGE,
                RabbitMQConfig.KILL_DIRECT_ROUTING_KEY,
                orderMessage);
        // 等待完成队列
        return JsonResult.delayOrder();
    }

    @GetMapping(value = "/result")
    @ResponseBody
    public JsonResult<Integer> killResult(@RequestParam("killId") Integer killId,
                                          HttpSession session) {
        if (killId == null || killId < 1) {
            return JsonResult.errorMsg("无此商品id");
        }
        // 判断该用户是否登录(拦截器判断),是否已经购买过此商品
        String userName = (String) session.getAttribute("user");
        UserInfo userInfo = userInfoService.getUserByName(userName);
        if (userInfo == null) { // 拦截器应该已经对session中的userName做了判断处理
            return JsonResult.errorMsg("登录信息错误");
        }
        // 查询订单信息
        Integer orderInfoId = killOrderInfoService.getOrderIdByUserIdAndKillId(userInfo.getUserId(), killId);
        if (orderInfoId == null) {
            // rabbitMQ消费者还没有消费完
            return JsonResult.delayOrder();
        }
        // 将订单id返回
        return JsonResult.ok(orderInfoId);
    }

    /**
     * 商品支付详情页面(点击秒杀后自动跳转到这个页面)
     *
     * @param orderId
     * @param session
     * @return
     */
    @GetMapping("/pay/{orderId}")
    public String toKillPay(@PathVariable("orderId") Integer orderId, HttpSession session, Model model) {
        // 未登录直接进入重定向页面
        OrderInfo orderInfo = orderInfoService.getById(orderId);
        if (orderInfo == null) {
            model.addAttribute("errorMsg", "订单不存在");
            return "order_error";
        }
        // 判断登录用户是否为下单用户
        String userSessionMsg = (String) session.getAttribute("user");

        UserInfo userInfo = userInfoService.getUserByName(userSessionMsg);

        if (!userInfo.getUserId().equals(orderInfo.getUserId())) {
            // 登录用户与下单用户不匹配
            model.addAttribute("errorMsg", "登录账号与支付账号不匹配");
            return "order_error";
        }
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("testData", orderInfo.toString());
        return "order_pay";
    }

    /**
     * 支付请求(修改支付状态)
     *
     * @param session
     * @return
     */
    @PostMapping("/pay/{orderId}")
    @ResponseBody
    public JsonResult killPay(@PathVariable("orderId") Integer orderId, HttpSession session) {
        // 判断参数
        if (orderId == null || orderId < 1) {
            return JsonResult.errorMsg("请选择订单...");
        }
        // 判断订单
        OrderInfo orderInfo = orderInfoService.getById(orderId);
        if (orderInfo == null) {
            return JsonResult.errorMsg("没有此订单信息...");
        }
        // 判断是否是下单用户付款
        String userSessionData = (String) session.getAttribute("user");
        UserInfo userInfo = userInfoService.getUserByName(userSessionData);
        if (userInfo == null) {
            return JsonResult.errorMsg("请登录...");
        }
        if (!userInfo.getUserId().equals(orderInfo.getUserId())) {
            return JsonResult.errorMsg("非法请求...");
        }
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~开始支付~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        orderInfoService.payOrder(userInfo.getUserId(), orderInfo.getOrderId());
        // 支付成功
        return JsonResult.ok();
    }

    /**
     * 用户手动取消支付
     * <pre>
     *    1. 超过三分钟自动取消支付(RabbitMQ)
     *    2. 用户点击取消支付
     *      2.1 将用户的订单状态修改为关闭状态
     *      2.2 将redis中的库存数量+1
     * </pre>
     *
     * @return 是否成功取消订单
     * @see top.izayoirinn.domain.KillOrderInfo
     */
    @GetMapping("/cancelPay/{orderId}")
    @ResponseBody
    public JsonResult<Object> cancelPay(@PathVariable("orderId") Integer orderId, HttpSession session) {
        // 判断参数
        if (orderId == null || orderId < 1) {
            return JsonResult.errorMsg("请选择订单...");
        }
        // 判断订单
        OrderInfo orderInfo = orderInfoService.getById(orderId);
        if (orderInfo == null) {
            return JsonResult.errorMsg("没有此订单信息...");
        }
        // 判断是否是下单用户付款
        String userSessionData = (String) session.getAttribute("user");
        UserInfo userInfo = userInfoService.getUserByName(userSessionData);

        if (!userInfo.getUserId().equals(orderInfo.getUserId())) {
            throw new GlobalException("非法请求...");
        }

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~取消支付~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // 修改数据库中的订单状态
        orderInfoService.cancelOrder(userInfo.getUserId(), orderId);

        // 取消支付成功
        return JsonResult.ok();
    }

    /**
     * 返回秒杀付款列表页
     *
     * @return
     */
    @GetMapping("/pay/list")
    public String killPayList(HttpSession session, Model model) {
        String userName = (String) session.getAttribute("user");
        UserInfo userInfo = userInfoService.getUserByName(userName);
        if (userInfo == null) {
            throw new GlobalException("请登录...");
        }
        List<OrderInfo> orderInfoList = orderInfoService.getOrderInfoByUserId(userInfo.getUserId());
        model.addAttribute("orderInfoList", orderInfoList);
        return "order_list";
    }
}
