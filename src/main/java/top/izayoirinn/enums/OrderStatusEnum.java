package top.izayoirinn.enums;


/**
 * @author Rinn Izayoi
 * @date 2021/6/14 16:45
 */
public enum OrderStatusEnum {
    WAIT_PAY(1, "待付款"),
    WAIT_DELIVER(2, "已付款,待发货"),
    SUCCESS(3, "交易成功"),
    CLOSE(-1, "交易关闭");

    public final Integer state;
    public final String stateInfo;

    OrderStatusEnum(Integer type, String message) {
        this.state = type;
        this.stateInfo = message;
    }

    //通过状态返回一个枚举类型的对象
    public static OrderStatusEnum stateOf(int state) {
        for (OrderStatusEnum orderStatusEnum : values()) {
            if (orderStatusEnum.getState() == state) {
                return orderStatusEnum;
            }
        }
        return null;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}
