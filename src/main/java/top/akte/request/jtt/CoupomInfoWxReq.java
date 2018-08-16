package top.akte.request.jtt;

import lombok.Data;
import lombok.ToString;
import top.akte.request.common.WxRequest;

@Data
@ToString
public class CoupomInfoWxReq extends WxRequest {

    //商品优惠券
    private String url;

}
