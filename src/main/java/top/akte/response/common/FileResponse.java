package top.akte.response.common;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class FileResponse implements Serializable {
    private String key;

    private String url;
}
