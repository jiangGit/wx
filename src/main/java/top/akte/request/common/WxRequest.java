package top.akte.request.common;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class WxRequest implements Serializable {

    private String sessionId;

    private String openId;

}
