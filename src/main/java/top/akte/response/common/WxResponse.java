package top.akte.response.common;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class WxResponse<T> extends  CommonResponse<T>{
    private String sessionId;

    private String openId;
}
