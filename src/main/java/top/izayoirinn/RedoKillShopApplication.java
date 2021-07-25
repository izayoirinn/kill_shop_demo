package top.izayoirinn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("top.izayoirinn.mapper")
public class RedoKillShopApplication {

    /*
    重置一个新的git仓库
        https://blog.csdn.net/yc1022/article/details/56487680
     */
    public static void main(String[] args) {
        SpringApplication.run(RedoKillShopApplication.class, args);
    }

}
