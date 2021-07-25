package top.izayoirinn;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import top.izayoirinn.common.KillData;

import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class RedoKillShopApplicationTests {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    void contextLoads() {
        // 指定 lua 脚本，并且指定返回值类型
        DefaultRedisScript<Long> redisScript =
                new DefaultRedisScript<>(KillData.STOCK_KILL_LUA_SCRIPT, Long.class);
        // 参数一：redisScript，参数二：key列表，参数三：arg（可多个）
        List<String> keyList = new ArrayList<>();
        keyList.add(KillData.KILL_STOCK);
        Long result = redisTemplate.execute(redisScript, keyList, String.valueOf(4), "-1");
        System.out.println("结果:" + result);
    }

}
