package top.akte.entity.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class OpenIdVo implements Serializable {

    private String openId;

    private String sessionKey;

    private String unionId;

}
