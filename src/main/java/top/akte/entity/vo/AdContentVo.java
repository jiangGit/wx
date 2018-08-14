package top.akte.entity.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class AdContentVo {

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


}
