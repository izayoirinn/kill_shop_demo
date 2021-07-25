package top.izayoirinn.rabbit;

import lombok.Data;
import lombok.ToString;

/**
 * @author Rinn Izayoi
 * @date 2021/7/21 14:23
 */
@Data
@ToString
public class OrderMessage {
    private Integer userId;
    private Integer killId;
}
