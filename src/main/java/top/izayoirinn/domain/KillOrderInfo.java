package top.izayoirinn.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 * @TableName kill_order_info
 */
@TableName(value = "kill_order_info")
@Data
public class KillOrderInfo implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer killOrderId;

    /**
     *
     */
    private Integer userId;

    /**
     * 修改表结构
     */
    private Integer killId;

    /**
     *
     */
    private Integer orderId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}