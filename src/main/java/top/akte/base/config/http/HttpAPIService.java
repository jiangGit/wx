package top.akte.base.config.http;

import lombok.extern.log4j.Log4j;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Log4j
@Component
public class HttpAPIService {

    @Autowired
    private CloseableHttpClient httpClient;

    @Autowired
    private RequestConfig config;


    /**
     * 不带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
     *
     * @param url
     * @return
     */
    public String doGet(String url) {
        return doGet(url,null);
    }

    public  String doGet(String url,Map<String,String> headers) {
        return doGet(url,headers,null);
    }

    /**
     *
     * @param url
     * @param headers
     * @return
     */
    public  String doGet(String url,Map<String,String> headers,Map<String,String> resCookie) {
        // 声明 http get 请求
        HttpGet httpGet = new HttpGet(url);

        // 装载配置信息
        httpGet.setConfig(config);

        CloseableHttpResponse response = null;
        try {
            if (headers != null){
                for (String k : headers.keySet()){
                    httpGet.addHeader(k,headers.get(k));
                }
            }

            response = httpClient.execute(httpGet,HttpClientContext.create());

            HttpEntity entity = response.getEntity();
            Header ceheader = entity.getContentEncoding();

            if (ceheader != null) {
                HeaderElement[] codecs = ceheader.getElements();
                for (int i = 0; i < codecs.length; i++) {
                    if (codecs[i].getName().equalsIgnoreCase("gzip")) {
                        entity = new GzipDecompressingEntity(response.getEntity());
                    }
                }
            }

            String result = EntityUtils.toString(entity, "utf-8");
            if (resCookie != null){
                Header[]  ch =response.getHeaders("Set-Cookie");
                for (Header h : ch ){
                    String cs = h.getValue();
                    String[] arr = cs.split(";");
                    for (String c : arr){
                        if (c.indexOf("=")>0){
                            String[] arr2 = c.split("=");
                            resCookie.put(arr2[0],arr2[1]);
                        }
                    }
                }
            }
            EntityUtils.consume(entity);
            return result;
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        } finally {
            try {
                if (response != null)
                    response.close();
            } catch (IOException e) {
                log.error(e.getMessage(),e);
            }
        }
        return null;
    }


    /**
     * post请求URL获取内容
     *
     * @param url
     * @return
     */
    public  String doPost(String url, Map<String, Object> params) throws IOException {
        return doPost(url,params,null);
    }

    /**
     *  post请求URL获取内容
     *
     * @param url
     * @param params
     * @param header
     * @return
     * @throws IOException
     */
    public  String doPost(String url, Map<String, Object> params,Map<String,String> header) throws IOException {
        return doPost(url,params,header,false);
    }

    /**
     *  post请求URL获取内容
     * @param url
     * @param params
     * @param header
     */
    public  String doPost(String url, Map<String, Object> params,Map<String,String> header,boolean userSingleClient) {
        // 声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        // 加入配置信息
        httpPost.setConfig(config);
        CloseableHttpResponse response = null;
        CloseableHttpClient singleClient = null;
        try {
            // 判断map是否为空，不为空则进行遍历，封装from表单对象
            if (params != null) {
                List<NameValuePair> list = new ArrayList<NameValuePair>();
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                }
                // 构造from表单对象
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");

                // 把表单放到post里
                httpPost.setEntity(urlEncodedFormEntity);
            }

            if (header != null){
                for (String k : header.keySet()){
                    httpPost.addHeader(k,header.get(k));
                }
            }
            if (userSingleClient){
                response =httpClient.execute(httpPost, HttpClientContext.create());
            }else {
                singleClient = HttpClients.createDefault();
                response =singleClient.execute(httpPost, HttpClientContext.create());
            }

            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return null;
        } finally {
            try {
                if (response != null)
                    response.close();
            } catch (IOException e) {
                log.error(e.getMessage(),e);
            }
            try {
                if (singleClient != null){
                    singleClient.close();
                }
            }catch (Exception e){
                log.error(e.getMessage(),e);
            }
        }
    }


    public String doPostJson(String url, String jsonStr,Map<String,String> header){
        // 声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        // 加入配置信息
        httpPost.setConfig(config);

        CloseableHttpResponse response = null;
        try {

            if (header != null){
                for (String k : header.keySet()){
                    httpPost.addHeader(k,header.get(k));
                }
            }
            StringEntity stringEntity = new StringEntity(jsonStr,"utf-8");//解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response =httpClient.execute(httpPost, HttpClientContext.create());
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return null;
        } finally {
            try {
                if (response != null)
                    response.close();
            } catch (IOException e) {
                log.error(e.getMessage(),e);
            }
        }
    }


}
