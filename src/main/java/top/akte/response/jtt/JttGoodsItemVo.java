package top.akte.response.jtt;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class JttGoodsItemVo {
    private Long goodsId; //商品SKU
    private String goodsLink; //商品链接
    private String goodsImg; //商品主图
    private Float goodsPrice; //商品原价
    private Float couponPrice; //商品最终价格
    private  Long discountStart; //优惠券开始时间(时间戳)
    private  Long discountEnd; //优惠券到期时间(时间戳)
    private Float discountPrice; //优惠券面额
    private String discountLink; //领券链接
    private String goodsContent; //商品文案
    private  Integer goodsType; //商品类别
    private Float commission; //商品佣金比例
    private Integer goodsLevel; //精选好货
    private Integer shopType; //0=普通产品1=京东配送
}
