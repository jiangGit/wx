package top.akte.service.wx;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.akte.base.config.http.HttpAPIService;
import top.akte.request.pdd.PddGoodsDetailWxReq;
import top.akte.request.pdd.PddSearchGoodsWxReq;
import top.akte.response.common.PageRes;
import top.akte.response.common.ResponseCodeConstant;
import top.akte.response.common.WxResponse;
import top.akte.response.jtt.JttGoodsDetailVo;
import top.akte.response.pdd.PddGoodsItemVo;
import top.akte.util.JacksonMapper;
import top.akte.util.MD5;
import top.akte.util.WxException;

import java.io.IOException;
import java.util.*;

@Log4j
@Service
public class PddWxService {

    @Value("${pdd.appId}")
    private String appId;

    @Value("${pdd.appKey}")
    private String appKey;

    @Value("${pdd.url}")
    private String url;

    @Autowired
    private HttpAPIService httpAPIService;


    public WxResponse<PageRes<PddGoodsItemVo>> searchGoods(PddSearchGoodsWxReq req) throws IOException {
        String type = "pdd.ddk.goods.search";
        WxResponse response = new WxResponse();
        PageRes<PddGoodsItemVo> pageRes = new PageRes<>();
        Map<String,Object> param = getPubParams(type);
        if (StringUtils.isNotBlank(req.getKeyword())){
            param.put("keyword",req.getKeyword());
        }
        if (req.getOptId() != null){
            param.put("opt_id",req.getOptId());
        }
        param.put("sort_type",req.getSortType());
        param.put("with_coupon",req.getWithCoupon());
        if (req.getCatId() != null){
            param.put("cat_id",req.getCatId());
        }
        if (req.getZsDuoId() != null){
            param.put("zs_duo_id",req.getZsDuoId());
        }
        if (req.getMerchantType() != null){
            param.put("merchant_type",req.getMerchantType());
        }
        param.put("page",req.getPage());
        param.put("page_size",req.getPageSize());
        param.put("sign",getSign(param));
        String json = httpAPIService.doPost(url,param);
        log.info("京推推请求结果："+json);
        JSONObject res = JSONObject.parseObject(json);
        if (res.get("goods_search_response") == null){
            throw new WxException(ResponseCodeConstant.SYS_EXCEPTION.getResponseCode(),res.getJSONObject("error_response").getString("error_msg"));
        }
        JSONObject result = res.getJSONObject("goods_search_response");
        List<PddGoodsItemVo> list = JacksonMapper.parseObjectWithUnderScores(JSONObject.toJSONString(result.getJSONArray("goods_list")),new TypeReference<List<PddGoodsItemVo>>(){});
        pageRes.setList(list);
        pageRes.setTotalCount(result.getInteger("total_count"));
        response.setResult(pageRes);
        return response;
    }


    public WxResponse<PddGoodsItemVo> goodsDetail(PddGoodsDetailWxReq req) throws IOException {
        String type = "pdd.ddk.goods.detail";
        WxResponse<PddGoodsItemVo> response = new WxResponse();
        Map<String,Object> param = getPubParams(type);
        param.put("goods_id_list",String.format("[%s]",req.getGoodsId()));
        param.put("sign",getSign(param));
        String json = httpAPIService.doPost(url,param);
        log.info("京推推请求结果："+json);
        JSONObject res = JSONObject.parseObject(json);
        if (res.get("goods_detail_response") == null){
            throw new WxException(ResponseCodeConstant.SYS_EXCEPTION.getResponseCode(),res.getJSONObject("error_response").getString("error_msg"));
        }
        JSONObject result = res.getJSONObject("goods_detail_response");
        PddGoodsItemVo detailVo = JacksonMapper.parseObject(JSONObject.toJSONString(result),new TypeReference<PddGoodsItemVo>(){});
        response.setResult(detailVo);
        return response;
    }


    private Map<String,Object> getPubParams(String type){
        Map<String, Object>  params = new HashMap<>();
        params.put("type",type);
        params.put("client_id",appId);
        params.put("timestamp",String.valueOf(new Date().getTime()/1000));
        params.put("data_type","JSON");
        return params;
    }

    /**
     * 计算签名
     * @param params
     * @return
     */
    private String getSign(Map<String, Object>  params){
        Map<String, String> map = new HashMap<String, String>();
        if (params == null || params.size() <= 0) {
            return null;
        }else{
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                Object value = params.get(key);
                if (value == null || value.toString().equals("") || key.equalsIgnoreCase("sign")) {
                    continue;
                }
                map.put(key, value.toString());
            }
            List<String> keys = new ArrayList<String>(map.keySet());
            Collections.sort(keys);
            StringBuffer sb = new StringBuffer();
            sb.append(appKey);
            for (int i = 0; i < keys.size(); i++) {
                String key = keys.get(i);
                String value = map.get(key);
                sb.append(value);
            }
            sb.append(appKey);
            return MD5.MD5Encode(sb.toString());
        }
    }
}
