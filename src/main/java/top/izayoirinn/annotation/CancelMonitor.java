package top.izayoirinn.annotation;

import java.lang.annotation.*;

/**
 * 取消{@link Monitor}的日志记录
 *
 * @author Rinn Izayoi
 * @date 2021/7/8 17:33
 */
@Target(value = {ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CancelMonitor {
    String value() default "";
}
