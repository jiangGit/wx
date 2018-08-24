package top.akte.service.wx;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.akte.base.config.http.HttpAPIService;
import top.akte.request.pdd.*;
import top.akte.response.common.PageRes;
import top.akte.response.common.ResponseCodeConstant;
import top.akte.response.common.WxResponse;
import top.akte.response.pdd.*;
import top.akte.util.JacksonMapper;
import top.akte.util.MD5;
import top.akte.util.WxException;

import java.io.IOException;
import java.util.*;

@Log4j
@Service
/**
 * 拼多多
 */
public class PddWxService {

    @Value("${pdd.appId}")
    private String appId;

    @Value("${pdd.appKey}")
    private String appKey;

    @Value("${pdd.url}")
    private String url;

    @Autowired
    private HttpAPIService httpAPIService;

    /**
     * 查询类别列表
     * @param req
     * @return
     * @throws IOException
     */
    public WxResponse<List<PddGoodsCatItemVo>> catList(PddCatListWxReq req)throws IOException{
        String type = "pdd.goods.cats.get";
        WxResponse<List<PddGoodsCatItemVo>> response = new WxResponse();
        Map<String,Object> param = getPubParams(type);
        param.put("parent_cat_id",req.getParentCatId());
        param.put("sign",getSign(param));
        String json = httpAPIService.doPost(url,param);
        log.info("京推推请求结果："+json);
        JSONObject res = JSONObject.parseObject(json);
        if (res.get("goods_cats_get_response") == null){
            throw new WxException(ResponseCodeConstant.SYS_EXCEPTION.getResponseCode(),res.getJSONObject("error_response").getString("error_msg"));
        }
        JSONObject result = res.getJSONObject("goods_cats_get_response");
        List<PddGoodsCatItemVo> list = JacksonMapper.parseObjectWithUnderScores(JSONObject.toJSONString(result.getJSONArray("goods_cats_list")),new TypeReference<List<PddGoodsCatItemVo>>(){});
        response.setResult(list);
        return response;
    }

    /**
     * 查询标签列表
     * @param req
     * @return
     * @throws IOException
     */
    public WxResponse<List<PddGoodsOptItemVo>> optList(PddOptListWxReq req)throws IOException{
        String type = "pdd.goods.opt.get";
        WxResponse<List<PddGoodsOptItemVo>> response = new WxResponse();
        Map<String,Object> param = getPubParams(type);
        param.put("parent_opt_id",req.getParentOptId());
        param.put("sign",getSign(param));
        String json = httpAPIService.doPost(url,param);
        log.info("京推推请求结果："+json);
        JSONObject res = JSONObject.parseObject(json);
        if (res.get("goods_opt_get_response") == null){
            throw new WxException(ResponseCodeConstant.SYS_EXCEPTION.getResponseCode(),res.getJSONObject("error_response").getString("error_msg"));
        }
        JSONObject result = res.getJSONObject("goods_opt_get_response");
        List<PddGoodsOptItemVo> list = JacksonMapper.parseObjectWithUnderScores(JSONObject.toJSONString(result.getJSONArray("goods_opt_list")),new TypeReference<List<PddGoodsOptItemVo>>(){});
        response.setResult(list);
        return response;
    }

    /**
     * 商品列表查询
     * @param req
     * @return
     * @throws IOException
     */
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

    /**
     * 商品详情
     * @param req
     * @return
     * @throws IOException
     */
    public WxResponse<PddGoodsDetailVo> goodsDetail(PddGoodsDetailWxReq req) throws IOException {
        String type = "pdd.ddk.goods.detail";
        WxResponse<PddGoodsDetailVo> response = new WxResponse();
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
        PddGoodsDetailVo detailVo = JacksonMapper.parseObjectWithUnderScores(JSONObject.toJSONString(result.getJSONArray("goods_details").getJSONObject(0)),new TypeReference<PddGoodsDetailVo>(){});
        response.setResult(detailVo);
        return response;
    }

    /**
     * 生成推广地址
     * @param req
     * @return
     * @throws IOException
     */
    public WxResponse<String > genQrcode(PddGenQrcodeWxReq req)throws IOException{
        String type = "pdd.ddk.weapp.qrcode.url.gen";
        WxResponse response = new WxResponse();
        Map<String,Object> param = getPubParams(type);
        param.put("p_id",req.getPid());
        param.put("custom_parameters","pdd");
        param.put("goods_id_list",String.format("[%s]",req.getGoodsId()));
        param.put("sign",getSign(param));
        String json = httpAPIService.doPost(url,param);
        log.info("京推推请求结果："+json);
        JSONObject res = JSONObject.parseObject(json);
        if (res.get("weapp_qrcode_generate_response") == null){
            throw new WxException(ResponseCodeConstant.SYS_EXCEPTION.getResponseCode(),res.getJSONObject("error_response").getString("error_msg"));
        }
        response.setResult(res.getJSONObject("weapp_qrcode_generate_response").getString("url"));
        return response;
    }

    /**
     * 查询已经生成的推广位信息
     * @param req
     * @return
     * @throws IOException
     */
    public WxResponse<PageRes<PddPItemVo>> queryPid(PddQueryPidWxReq req) throws IOException {
        String type = "pdd.ddk.goods.pid.query";
        WxResponse response = new WxResponse();
        PageRes<PddPItemVo> pageRes = new PageRes<>();
        Map<String,Object> param = getPubParams(type);
        param.put("page",req.getPage());
        param.put("page_size",req.getPageSize());
        param.put("sign",getSign(param));
        String json = httpAPIService.doPost(url,param);
        log.info("京推推请求结果："+json);
        JSONObject res = JSONObject.parseObject(json);
        if (res.get("p_id_query_response") == null){
            throw new WxException(ResponseCodeConstant.SYS_EXCEPTION.getResponseCode(),res.getJSONObject("error_response").getString("error_msg"));
        }
        JSONObject result = res.getJSONObject("p_id_query_response");
        List<PddPItemVo> list = JacksonMapper.parseObjectWithUnderScores(JSONObject.toJSONString(result.getJSONArray("p_id_list")),new TypeReference<List<PddPItemVo>>(){});
        pageRes.setList(list);
        pageRes.setTotalCount(result.getInteger("total_count"));
        response.setResult(pageRes);
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
                sb.append(key).append(value);
            }
            sb.append(appKey);
            return MD5.MD5Encode(sb.toString()).toUpperCase();
        }
    }
}
