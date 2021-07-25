package top.izayoirinn.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @TableName order_info
 */
@TableName(value = "order_info")
@Data
public class OrderInfo implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer orderId;

    /**
     *
     */
    private Integer userId;

    /**
     *
     */
    private Integer goodsId;

    /**
     *
     */
    private String goodsName;

    /**
     *
     */
    private Integer goodsCount;

    /**
     *
     */
    private BigDecimal goodsPrice;

    /**
     *
     */
    private Integer orderStatus;

    /**
     *
     */
    private Date createDate;

    /**
     *
     */
    private Date payDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}