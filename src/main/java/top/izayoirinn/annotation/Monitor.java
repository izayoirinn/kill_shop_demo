package top.izayoirinn.annotation;

import java.lang.annotation.*;

/**
 * 被标记的方法会被监视方法的执行的运行时间(rt)
 * 当标记在类上时会对所有方法监视
 * 取消日志记录 {@link CancelMonitor}
 *
 * @author Rinn Izayoi
 * @date 2021/7/8 13:50
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Monitor {

    String value() default "";
}
