package top.akte.response.pdd;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PddGenUrlVo {

    /**
     *唤起微信app推广短链接
     */
    private String weAppWebViewShortUrl;

    /**
     *唤起微信app推广链接
     */
    private String weAppWebViewUrtUrl;

    /**
     * 唤醒拼多多app的推广短链接
     */
    private String mobileShortUrl;

    /**
     *唤醒拼多多app的推广长链接
     */
    private String mobileUrl;

    /**
     *推广链短接
     */
    private  String shortUrl;

    /**
     *推广长链接
     */
    private String url;
}
