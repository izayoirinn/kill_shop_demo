package top.izayoirinn.exception;

/**
 * @author Rinn Izayoi
 * @date 2021/7/21 16:18
 */
public class RabbitReQueueException extends GlobalException {
    public RabbitReQueueException() {
        super();
    }

    public RabbitReQueueException(String message) {
        super(message);
    }
}
