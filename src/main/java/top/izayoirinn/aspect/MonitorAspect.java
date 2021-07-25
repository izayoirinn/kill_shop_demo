package top.izayoirinn.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 对rt日志记录
 *
 * @author Rinn Izayoi
 * @date 2021/7/8 18:17
 */
@Component
@Aspect
@Slf4j
public class MonitorAspect {

    /**
     * {@link top.izayoirinn.annotation.Monitor}标记的类的所有方法以及被标记的方法会记录rt日志
     * {@link top.izayoirinn.annotation.CancelMonitor}标记的方法会取消记录rt日志
     * 取消对get/set的日志记录
     *
     * @param joinPoint
     * @return
     */
    @Around(value = "(@annotation(top.izayoirinn.annotation.Monitor)||@within(top.izayoirinn.annotation.Monitor))" +
            "&&(!@annotation(top.izayoirinn.annotation.CancelMonitor)&&!execution(* set*(..))&&!execution(* get*(..))))")
    public Object monitorLog(ProceedingJoinPoint joinPoint) {
        // 记录开始时间
        long beginTime = System.currentTimeMillis();
        // 执行目标方法
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("====== {}.{} 执行错误:{} ======",
                    joinPoint.getTarget().getClass(),
                    joinPoint.getSignature().getName(), throwable.getMessage());
        }
        //记录结束时间
        long endTime = System.currentTimeMillis();
        long takeTime = endTime - beginTime;

        if (takeTime > 3000) {
            log.error("====== {}.{} 执行超时,耗时:{}毫秒,可能出现异常信息 ======",
                    joinPoint.getTarget().getClass(),
                    joinPoint.getSignature().getName(), takeTime);
        } else {
            log.info("====== {}.{} 执行结束,耗时:{}毫秒 ======",
                    joinPoint.getTarget().getClass(),
                    joinPoint.getSignature().getName(), takeTime);
        }

        if (null == proceed) {
            log.warn("====== {}.{} 执行结果为null ======",
                    joinPoint.getTarget().getClass(),
                    joinPoint.getSignature().getName());
        }
        return proceed;
    }

    /**
     * !execution(* set*(..))
     * !execution(* get*(..))
     */
}
