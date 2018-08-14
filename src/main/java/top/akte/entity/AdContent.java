package top.akte.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;


@Data
@ToString
@Table(name = "ad_content")
public class AdContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String adModuleId;

    private String title;

    private String link;

    /**
     * //内容类型(1:图片，2:文字，3:商品)
     */
    private Integer type;

    private String goodsId;

    private String imageUrl;

    private String content;

    private Integer sortWeight;

    private Date timeStart;

    private Date timeEnd;
    /**
     * 1未上架，2 上架
     */
    private Integer status;

    private Date createTime;

    private Date updateTime;


}
