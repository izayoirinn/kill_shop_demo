package top.izayoirinn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.izayoirinn.interceptor.UserInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    String[] addPathPatterns = {
            "/seckill/**"
    };
    String[] excludePathPatterns = {
            "/user/login",
            "/login",
            "/user/logout",
            "/seckill/list",
            "/seckill/view/**",
            "/seckill/refreshStock/**"
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor())
                .addPathPatterns(addPathPatterns).excludePathPatterns(excludePathPatterns);
    }

    @Bean
    public UserInterceptor userInterceptor() {
        return new UserInterceptor();
    }

}
