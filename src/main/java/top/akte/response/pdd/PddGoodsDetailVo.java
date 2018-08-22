package top.akte.response.pdd;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class PddGoodsDetailVo {

    /**
     * 服务评分击败同类店铺百分比
     */
    private Double servPct;

    /**
     * 物流评分击败同类店铺百分比
     */
    private Double lgstPct;

    /**
     * 描述评分击败同类店铺百分比
     */
    private Double descPct;

    /**
     * 商品标签ID
     */
    private List optIds;

    /**
     * 店铺类型，1-个人，2-企业，3-旗舰店，4-专卖店，5-专营店，6-普通店
     */
    private Integer merchantType;

    /**
     * 服务评分
     */
    private Long vgServ;

    /**
     * 物流评分
     */
    private Long avgLgst;

    /**
     * 描述评分
     */
    private Long avgDesc;

    /**
     * 商品详情图列表
     */
    private String goodsGalleryUrls;

    /**
     * 商品标签名
     */
    private String optName;

    /**
     * 商品标签ID，格式为：[一级标签ID，二级标签ID，三级标签ID...]使用pdd.goods.opt.get获取
     */
    private Integer optId;

    /**
     * 商品类目ID，
     */
    private Integer catIds;

    /**
     * 商品评价数量
     */
    private Integer goodsEvalCount;

    /**
     * 商品评价分
     */
    private Integer goodsEvalScore;

    /**
     * 佣金比例，千分比
     */
    private Integer promotionRate;

    /**
     * 优惠券失效时间，UNIX时间戳
     */
    private Long couponEndTime;

    /**
     * 优惠券生效时间，UNIX时间戳
     */
    private Long couponStartTime;

    /**
     * 优惠券剩余数量
     */
    private Integer couponRemainQuantity;

    /**
     * 优惠券总数量
     */
    private Integer couponTotalQuantity;

    /**
     * 优惠券面额，单位为分
     */
    private Integer couponDiscount;

    /**
     * 优惠券门槛价格，单位为分
     */
    private Integer couponMinOrderAmount;

    /**
     * 店铺名称
     */
    private String mallName;

    /**
     * 最小单买价格，单位为分
     */
    private Integer minNormalPrice;

    /**
     * 最小拼团价格，单位为分
     */
    private Integer minGroupPrice;

    /**
     * 已售卖件数
     */
    private Integer soldQuantity;

    /**
     * 商品主图
     */
    private String goodsImageUrl;

    /**
     * 商品缩略图
     */
    private String goodsThumbnailUrl;

    /**
     * 商品标题
     */
    private String goodsName;

    /**
     * 参与多多进宝的商品ID
     */
    private Long goodsId;

}
