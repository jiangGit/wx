package top.akte.request.jtt;

import lombok.Data;
import lombok.ToString;
import top.akte.request.common.WxRequest;

@Data
@ToString
public class GoodsLinkReq extends WxRequest {


    private Long unionId;//联盟ID(必填)

    private Long positionId;//推广位ID

    private Long gid;//商品ID

    private String couponUrl;//优惠券链接(为空则位单品转链)

    private String getKey(){
        return String.format("%s_%s_%s_%s",gid,unionId,couponUrl,positionId);
    }
}
