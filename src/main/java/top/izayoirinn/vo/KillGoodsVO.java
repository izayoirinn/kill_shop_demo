package top.izayoirinn.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Rinn Izayoi
 * @date 2021/7/18 16:09
 */
@Data
@ToString
public class KillGoodsVO {

    private Integer killId;

    private Integer goodsId;

    // 在redis中单独使用hash存储库存数量
    @JsonIgnore
    private Integer stockCount;

    private Date startDate;

    private Date endDate;

    private BigDecimal killPrice;

    private String goodsName;

    private String goodsImg;

    private String goodsPrice;

}
