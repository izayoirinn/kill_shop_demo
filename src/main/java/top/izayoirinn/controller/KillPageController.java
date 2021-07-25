package top.izayoirinn.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import top.izayoirinn.common.JsonResult;
import top.izayoirinn.common.KillData;
import top.izayoirinn.domain.KillGoods;
import top.izayoirinn.service.KillGoodsService;
import top.izayoirinn.utils.JsonUtils;
import top.izayoirinn.utils.RedisOperator;
import top.izayoirinn.vo.KillGoodsVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Rinn Izayoi
 * @date 2021/7/18 15:58
 * 商品秒杀页面的处理
 */
@Controller
@RequestMapping("/seckill")
public class KillPageController {
    @Autowired
    private KillGoodsService killGoodsService;
    @Autowired
    private RedisOperator redisOperator;
    @Autowired
    private TemplateEngine templateEngine;

    /**
     * 秒杀商品列表页面
     *
     * @return 视图页面
     */
    @GetMapping("/list")
    @ResponseBody
    public String toList(HttpServletRequest request, HttpServletResponse response,
                         Model model) {
        String listViewHtml;
        // 从redis中获取 秒杀商品列表页
        listViewHtml = redisOperator.get(KillData.KILL_VIEW_LIST);
        if (StringUtils.isNotBlank(listViewHtml)) {
            return listViewHtml;
        } else {
            synchronized (this) {
                // 从redis中获取 秒杀商品列表页
                listViewHtml = redisOperator.get(KillData.KILL_VIEW_LIST);
                if (StringUtils.isNotBlank(listViewHtml)) {
                    return listViewHtml;
                } else {
                    // 访问数据库获取秒杀商品信息
                    List<KillGoodsVO> goodsList = killGoodsService.listKillGoods();
                    // 非空判断
                    if (goodsList == null || goodsList.size() == 0) {
                        redisOperator.set(KillData.KILL_VIEW_LIST, KillData.ERROR_HTML, 60 * 3);
                        return KillData.ERROR_HTML;
                    }
                    // 秒杀商品列表页面数据缓存
                    model.addAttribute("goodsList", goodsList);
                    WebContext context = new WebContext(request, response, request.getServletContext(),
                            request.getLocale(), model.asMap());
                    listViewHtml = templateEngine.process("goods_list", context);
                    // 将数据缓存到redis中
                    redisOperator.set(KillData.KILL_VIEW_LIST, listViewHtml, 60 * 20);
                }
            }
        }
        return listViewHtml;
    }

    /**
     * 秒杀商品详情页面
     * <pre>
     *  1. 查询redis中有无页面信息
     *  2. 从数据库中查询商品信息
     *  3. 将商品信息渲染到html页面上
     *      3.1 ? 将商品信息保存到redis中{@link #refreshStock}
     *  4. 将页面保存到redis中
     *  5. 返回页面
     * </pre>
     *
     * @return 秒杀商品详情页 | 错误404页面
     */
    @GetMapping("/view/{id}")
    @ResponseBody
    public String toView(@PathVariable("id") Integer killId, Model model,
                         HttpServletRequest request, HttpServletResponse response) {
        // 参数验证
        if (killId == null || killId < 1) {
            return KillData.ERROR_HTML;
        }
        // 秒杀商品视图页面数据
        String goodsViewHtml;
        goodsViewHtml = redisOperator.get(KillData.KILL_VIEW_GOODS + ":" + killId);
        if (StringUtils.isNotBlank(goodsViewHtml)) {
            return goodsViewHtml;
        } else {
            synchronized (this) {
                goodsViewHtml = redisOperator.get(KillData.KILL_VIEW_GOODS + ":" + killId);
                if (StringUtils.isNotBlank(goodsViewHtml)) {
                    return goodsViewHtml;
                } else {
                    KillGoodsVO killGoods = killGoodsService.getKillGoodsByKillId(killId);
                    // 非空判断
                    if (killGoods == null) {
                        // 存储404页面
                        redisOperator.set(KillData.KILL_VIEW_GOODS + ":" + killId, KillData.ERROR_HTML, 60 * 3);

                       /*
                       // 数据库中没有秒杀商品,商品数据放置null
                        redisOperator.set(KillData.KILL_GOODS_DATA + ":" + killId,
                                "null", 60 * 3);
                        */

                        return KillData.ERROR_HTML;
                    }

                    // 秒杀商品详情页面数据缓存
                    model.addAttribute("goods", killGoods);
                    WebContext context = new WebContext(request, response, request.getServletContext(),
                            request.getLocale(), model.asMap());
                    goodsViewHtml = templateEngine.process("goods_kill", context);
                    // 将静态页面存放进入到redis中
                    redisOperator.set(KillData.KILL_VIEW_GOODS + ":" + killId, goodsViewHtml, 60 * 20);

                }
            }
        }
        return goodsViewHtml;
    }

    /**
     * 页面库存刷新
     * <pre>
     *     1. 查询redis中的商品信息
     *     2. 如果redis中没有信息,查询数据库中的商品信息 {@link #toView}
     *     3. 将数据库中的商品信息添加到redis中
     *     4. 返回最新的商品库存数量
     *     5. 如果数据库中也没有商品信息，在redis中放置空串防止恶意请求
     * </pre>
     *
     * @param killId 秒杀商品id
     * @return redis中最新的库存数量
     */
    @GetMapping("/refreshStock/{killId}")
    @ResponseBody
    public JsonResult<Integer> refreshStock(@PathVariable("killId") Integer killId) {
        // 参数校验
        if (killId == null || killId < 1) {
            return JsonResult.errorMsg("请选择秒杀商品...");
        }
        int stockCount;
        // 从hash中获取到库存数量
        String stockCountStr = redisOperator.hGet(KillData.KILL_STOCK, String.valueOf(killId));
        if (StringUtils.isNotBlank(stockCountStr)) {
            stockCount = Integer.parseInt(stockCountStr);
        } else {
            // redis中没有存储库存数据
            synchronized (this) {
                stockCountStr = redisOperator.hGet(KillData.KILL_STOCK, String.valueOf(killId));
                if (StringUtils.isNotBlank(stockCountStr)) {
                    stockCount = Integer.parseInt(stockCountStr);
                } else {
                    KillGoods killGoods = killGoodsService.getById(killId);
                    // 从数据库查询,发现没有此商品数据,将商品数量置为0
                    stockCount = killGoods == null ? 0 : killGoods.getStockCount();
                    String killIdStr = String.valueOf(killId);
                    redisOperator.hSet(KillData.KILL_STOCK, killIdStr, String.valueOf(stockCount));
                }
            }
        }
        return JsonResult.ok(stockCount);
    }
}
