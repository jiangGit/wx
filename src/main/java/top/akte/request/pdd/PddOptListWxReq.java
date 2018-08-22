package top.akte.request.pdd;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class PddOptListWxReq {

    private Integer parentOptId = 0;
}
