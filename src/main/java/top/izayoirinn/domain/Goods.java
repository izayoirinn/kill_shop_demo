package top.izayoirinn.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 
 * @TableName goods
 */
@TableName(value ="goods")
@Data
public class Goods implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer goodsId;

    /**
     * 
     */
    private String goodsName;

    /**
     * 
     */
    private String goodsImg;

    /**
     * 
     */
    private String goodsContent;

    /**
     * 
     */
    private BigDecimal goodsPrice;

    /**
     * 
     */
    private Integer goodsStock;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}