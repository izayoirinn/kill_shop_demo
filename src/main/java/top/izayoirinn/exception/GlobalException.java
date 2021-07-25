package top.izayoirinn.exception;

/**
 * @author Rinn Izayoi
 * @date 2021/7/18 15:39
 */
public class GlobalException extends RuntimeException {
    public GlobalException() {
        super();
    }

    public GlobalException(String message) {
        super(message);
    }
}
