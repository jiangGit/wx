package top.akte.request.customer;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;
import top.akte.request.common.WxRequest;

@Data
@ToString
public class GetOpenIdWxReq extends WxRequest {

    @NotBlank
    private String code;

    @NotBlank
    private String grantType = "authorization_code";

}
