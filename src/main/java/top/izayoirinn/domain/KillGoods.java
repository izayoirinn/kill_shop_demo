package top.izayoirinn.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName kill_goods
 */
@TableName(value ="kill_goods")
@Data
public class KillGoods implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer killId;

    /**
     * 
     */
    private Integer goodsId;

    /**
     * 
     */
    private Integer stockCount;

    /**
     * 
     */
    private Date startDate;

    /**
     * 
     */
    private Date endDate;

    /**
     * 
     */
    private BigDecimal killPrice;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}