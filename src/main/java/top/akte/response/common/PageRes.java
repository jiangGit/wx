package top.akte.response.common;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
public class PageRes<T>  {
    private List<T> list;
    private long totalCount;
}
