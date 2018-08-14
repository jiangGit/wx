package top.akte.entity.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class WxUserInfoVo implements Serializable {
    private String openId;
    private String userName;
    private String avatar;
}
