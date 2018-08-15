package top.akte.service.wx;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.akte.base.config.http.HttpAPIService;
import top.akte.request.jtt.JttGoodsDetailWxReq;
import top.akte.request.jtt.JttGoodsListWxReq;
import top.akte.response.common.PageRes;
import top.akte.response.common.ResponseCodeConstant;
import top.akte.response.common.WxResponse;
import top.akte.response.jtt.JttGoodsDetailVo;
import top.akte.response.jtt.JttGoodsItemVo;
import top.akte.util.JacksonMapper;
import top.akte.util.WxException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JttGoodsWxService {
    @Autowired
    private HttpAPIService httpAPIService;

    @Value("${jtt.appId}")
    private String appId;

    @Value("${jtt.appKey}")
    private String appKey;

    public WxResponse<PageRes<JttGoodsItemVo>> goodsList(JttGoodsListWxReq req) throws IOException {
        WxResponse response = new WxResponse();
        PageRes<JttGoodsItemVo> pageRes = new PageRes<>();
        String url = "http://japi.jingtuitui.com/api/get_goods_list";
        Map<String,Object> param = new HashMap<>();
        param.put("appid",appId);
        param.put("appkey",appKey);
        if (req.getType() != null){
            param.put("type",req.getType());
        }
        if (StringUtils.isNotBlank(req.getSo())){
            param.put("so",req.getSo());
        }
        if (StringUtils.isNotBlank(req.getRank())){
            param.put("rank",req.getRank());
        }
        String json = httpAPIService.doPost(url,param);
        JSONObject res = JSONObject.parseObject(json);
        if (res.getInteger("return") != 0){
            throw new WxException(ResponseCodeConstant.SYS_EXCEPTION.getResponseCode(),res.getInteger("return")+"");
        }
        JSONObject result = res.getJSONObject("result");
        List<JttGoodsItemVo> list = JacksonMapper.parseObjectWithUnderScores(JSONObject.toJSONString(result.getJSONArray("data")),new TypeReference<List<JttGoodsItemVo>>(){});
        pageRes.setList(list);
        pageRes.setTotalPage(result.getInteger("total_page"));
        pageRes.setCurrentPage(result.getInteger("current_page"));
        response.setResult(pageRes);
        return response;
    }


    public WxResponse<JttGoodsDetailVo> goodsDetail(JttGoodsDetailWxReq req) throws IOException {
        WxResponse<JttGoodsDetailVo> response = new WxResponse<>();
        String url = "http://japi.jingtuitui.com/api/get_goods_info";
        Map<String,Object> param = new HashMap<>();
        param.put("appid",appId);
        param.put("appkey",appKey);
        param.put("gid",req.getGid());
        String json = httpAPIService.doPost(url,param);
        JSONObject res = JSONObject.parseObject(json);
        if (res.getInteger("return") != 0){
            throw new WxException(ResponseCodeConstant.SYS_EXCEPTION.getResponseCode(),res.getInteger("return")+"");
        }
        JSONObject result = res.getJSONObject("result");
        JttGoodsDetailVo detailVo = JacksonMapper.parseObject(JSONObject.toJSONString(result),new TypeReference<JttGoodsDetailVo>(){});
        response.setResult(detailVo);
        return response;
    }

}
