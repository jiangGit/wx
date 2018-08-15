package top.akte.request.jtt;

import lombok.Data;
import lombok.ToString;
import top.akte.request.common.WxRequest;

import javax.validation.constraints.NotNull;

@Data
@ToString
public class JttGoodsDetailWxReq extends WxRequest {
    @NotNull
    private Long gid;

}
