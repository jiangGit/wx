package top.akte.response.jtt;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class JttGoodsDetailVo {

    private Float commisionRatioPc; //PC佣金比例

    private Float commisionRatioWl;//无线佣金比例

    private Long endDate; //推广结束日期

    private String goodsName; //商品名称

    private String imgUrl; //图片地址

    private String materialUrl; //商品落地页

    private Integer shopId; //	店铺ID

    private Long skuId; //商品SKU

    private Long startDate; //推广开始日期

    private Float unitPrice; //商品单价即京东价

    private Float wlUnitPrice; //商品无线京东价

    private Integer cid; // 一级类别id

    private Integer cid2; // 二级类别id

    private Integer cid3; // 三级类别id

    private String cidName; // 一级类别

    private String cid2Name;// 二级类别

    private String cid3Name;// 三级类别
}
