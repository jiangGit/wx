package top.akte.request.jtt;

import lombok.Data;
import lombok.ToString;
import top.akte.request.common.WxRequest;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@ToString
public class JttGoodsListWxReq extends WxRequest {

    @Min(1L)
    private Integer pageNo = 1;

    @Min(1L)
    private Integer pageSize = 10;

    /**
     * 商品搜索：商品名称/商品SKU
     */
    private String so;

    /**
     * 商品分类：
     1 : 女装
     2 : 男装
     3 : 内衣配饰
     4 : 母婴玩具
     5 : 美妆个护
     6 : 食品保健
     7 : 居家生活
     8 : 鞋品箱包
     9 : 运动户外
     10 : 文体车品
     11 : 数码家电
     */
    private Integer type;

    /**
     * 商品筛选：
     self : 京东自营
     sift : 精选好货；
     wtype : 京东配送产品；
     finally : 券后价最低；
     brokerage : 佣金比例最高；
     sale : 优惠最多；
     */
    private String rank;


    public String getKey(){
        return String.format("%s_%s_%s_%s_%s",type,rank,so,pageNo,pageSize);
    }

}
