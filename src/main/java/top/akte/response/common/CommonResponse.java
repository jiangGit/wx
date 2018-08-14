package top.akte.response.common;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class CommonResponse<T> implements Serializable {

    private T result;
    private String code;
    private String content;
}
