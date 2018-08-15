package top.akte.request.ad;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;
import top.akte.request.common.WxRequest;

import javax.validation.constraints.Min;

@Data
@ToString
public class AdListWxRequest extends WxRequest {

    @NotBlank(message = "adModuleId不能为空")
    private String adModuleId;

    @Min(1L)
    private Integer pageNo = 1;

    @Min(1L)
    private Integer pageSize = 10;



}
