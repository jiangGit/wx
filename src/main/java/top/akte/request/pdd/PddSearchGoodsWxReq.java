package top.akte.request.pdd;

import lombok.Data;
import lombok.ToString;
import top.akte.request.common.WxRequest;

@Data
@ToString
public class PddSearchGoodsWxReq extends WxRequest {
    /**
     * 商品关键词 与opt_id字段选填一个或全部填写
     */
    private String keyword;
    /**
     * 商品标签类目ID 非必填
     */
    private Integer optId;
    /**
     * 商品类目ID
     */
    private Integer catId;

    private Integer page = 1;

    private Integer pageSize = 10;

    /**
     * 排序方式:0-综合排序;1-按佣金比率升序;2-按佣金比例降序;3-按价格升序;4-按价格降序;5-按销量升序;
     * 6-按销量降序;7-优惠券金额排序升序;8-优惠券金额排序降序;9-券后价升序排序;10-券后价降序排序;
     * 11-按照加入多多进宝时间升序;12-按照加入多多进宝时间降序;13-按佣金金额升序排序;14-按佣金金额降序排序;
     * 15-店铺描述评分升序;16-店铺描述评分降序;17-店铺物流评分升序;18-店铺物流评分降序;19-店铺服务评分升序;
     * 20-店铺服务评分降序;27-描述评分击败同类店铺百分比升序，28-描述评分击败同类店铺百分比降序，
     * 29-物流评分击败同类店铺百分比升序，30-物流评分击败同类店铺百分比降序，31-服务评分击败同类店铺百分比升序，
     * 32-服务评分击败同类店铺百分比降序
     */
    private Integer sortType = 0;

    /**
     * 是否只返回优惠券的商品，false返回所有商品，true只返回有优惠券的商品
     */
    private Boolean withCoupon = false;

    /**
     * 招商多多客ID
     */
    private Long zsDuoId;

    /**
     * 店铺类型，1-个人，2-企业，3-旗舰店，4-专卖店，5-专营店，6-普通店（未传为全部）
     */
    private Integer merchantType;

}
