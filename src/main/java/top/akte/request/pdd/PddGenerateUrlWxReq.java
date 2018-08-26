package top.akte.request.pdd;

import lombok.Data;
import lombok.ToString;
import top.akte.request.common.WxRequest;

@Data
@ToString(callSuper = true)
public class PddGenerateUrlWxReq extends WxRequest {

    private Integer goodsId;

    private String pid;

    /**
     * 是否生成短链接
     */
    private Boolean generateShortUrl =true;

    /**
     * 是否生成唤起微信客户端链接
     */
    private Boolean generateWeappWebview = true;

    /**
     * 自定义参数，为链接打上自定义标签
     */
    private String customParameters;
}
