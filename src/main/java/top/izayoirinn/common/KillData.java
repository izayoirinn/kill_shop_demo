package top.izayoirinn.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.PostConstruct;

/**
 * @author Rinn Izayoi
 * @date 2021/7/15 18:46
 */
@Component
public class KillData {

    @Autowired
    private TemplateEngine templateEngine;
    /**
     * Redis缓存数据库
     * <p>
     * 【使用Redis来缓存页面和相关数据】
     * <p>
     * 1.秒杀商品列表页	key  seckill:page:list   code
     * <p>
     * 2.秒杀商品详情页   key   seckill:page:1    code
     * <p>
     * 3.秒杀商品数据      key   seckill:killGoods:1
     * <p>
     * 4.秒杀商品库存数量 key  seckill:stock   1  100
     * <p>
     * 5.用户购买记录  key   seckill:order     1:1    uid:gid
     */

    // 1. 秒杀商品列表页
    public static final String KILL_VIEW_LIST = "kill:view:list";
    // 2. 秒杀商品详情页
    public static final String KILL_VIEW_GOODS = "kill:view:goods";
    // 3. 秒杀商品数据
    public static final String KILL_GOODS_DATA = "kill:goods:data";
    // 4. 秒杀商品库存数量
    public static final String KILL_STOCK = "kill:stock";
    // 5. 用户购买记录
    public static final String KILL_ORDER_USER = "kill:order:user";

    /**
     * 扣减商品时redis执行的lua脚本
     */
    public static final String STOCK_KILL_LUA_SCRIPT =
            "if redis.call('hget',KEYS[1],ARGV[1])>'0'  " +
                    "then return redis.call('hincrby',KEYS[1],ARGV[1],ARGV[2])   end";
    // 404页面
    public static String ERROR_HTML;

    @PostConstruct
    public void init() {
        Context context = new Context();
        // private String errorHtml="<script>location.href='/killError'</script>";
        ERROR_HTML = templateEngine.process("404", context);
    }

}
