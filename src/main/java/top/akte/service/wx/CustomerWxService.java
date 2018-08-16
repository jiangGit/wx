package top.akte.service.wx;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.akte.base.config.RedisConnection;
import top.akte.base.config.http.HttpAPIService;
import top.akte.entity.vo.OpenIdVo;
import top.akte.request.customer.GetOpenIdWxReq;
import top.akte.response.common.ResponseCodeConstant;
import top.akte.response.common.WxResponse;
import top.akte.util.WxException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Log4j
@Service
public class CustomerWxService {

    @Value("${wx.appId}")
    private String appId;

    @Value("${wx.secret}")
    private String appKey;

    @Autowired
    private HttpAPIService httpAPIService;

    @Autowired
    private RedisConnection redisConnection;

    private static final long T = 30;


    public WxResponse<OpenIdVo> getOpenId(GetOpenIdWxReq req) throws IOException {
        String url = "http://japi.jingtuitui.com/api/get_goods_list";
        Map<String,Object> param = new HashMap<>();
        param.put("appid",appId);
        param.put("secret",appKey);
        param.put("js_code",req.getCode());
        param.put("grant_type",req.getGrantType());
        String json = httpAPIService.doPost(url,param);
        log.info("微信请求结果："+json);
        JSONObject res = JSONObject.parseObject(json);
        if (res.containsKey("errcode")){
            throw new WxException(ResponseCodeConstant.SYS_EXCEPTION.getResponseCode(),res.getString("errmsg"));
        }
        OpenIdVo openIdVo = new OpenIdVo();
        openIdVo.setOpenId(res.getString("openid"));
        openIdVo.setSessionKey(res.getString("session_key"));
        if (res.containsKey("unionid")){
            openIdVo.setUnionId(res.getString("unionid"));
        }
        redisConnection.setEx(req.getSessionId(),openIdVo,T, TimeUnit.MINUTES);
        WxResponse<OpenIdVo> response = new WxResponse<>();
        response.setResult(openIdVo);
        response.setOpenId(openIdVo.getOpenId());
        return response;
    }



}
