package top.akte.request.pdd;

import lombok.Data;
import lombok.ToString;
import top.akte.request.common.WxRequest;

@Data
@ToString(callSuper = true)
public class PddGoodsDetailWxReq extends WxRequest {

    private Integer goodsId;

}
