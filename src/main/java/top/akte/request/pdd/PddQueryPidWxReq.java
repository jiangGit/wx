package top.akte.request.pdd;

import lombok.Data;
import lombok.ToString;
import top.akte.request.common.WxRequest;

@Data
@ToString(callSuper = true)
public class PddQueryPidWxReq  extends WxRequest {

    private Integer page = 1;

    private Integer pageSize = 50;

}
