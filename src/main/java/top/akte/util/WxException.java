package top.akte.util;

import lombok.Getter;

public class WxException extends RuntimeException {

    @Getter
    private String code;

    public WxException(String code, String message) {
        super(message);
        this.code = code;
    }
}
