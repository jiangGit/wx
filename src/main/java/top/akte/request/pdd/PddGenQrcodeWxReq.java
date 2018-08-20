package top.akte.request.pdd;

import lombok.Data;
import lombok.ToString;
import top.akte.request.common.WxRequest;

@Data
@ToString
public class PddGenQrcodeWxReq extends WxRequest {

    private Integer goodsId;
}
