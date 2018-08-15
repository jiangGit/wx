import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.akte.WxApplication;
import top.akte.base.config.http.HttpAPIService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WxApplication.class)
public class JttTest {
    @Autowired
    private HttpAPIService httpAPIService;

    @Value("${jtt.appId}")
    private String appId;

    @Value("${jtt.appKey}")
    private String appKey;

    //@Test
    public void goodsList() throws IOException {
        String url = "http://japi.jingtuitui.com/api/get_goods_list";
        Map<String,Object> param = new HashMap<>();
        param.put("appid",appId);
        param.put("appkey",appKey);
        String res = httpAPIService.doPost(url,param);
        System.out.println(res);
    }

    @Test
    public void goodsDitail() throws IOException {
        String url = "http://japi.jingtuitui.com/api/get_goods_info";
        Map<String,Object> param = new HashMap<>();
        param.put("appid",appId);
        param.put("appkey",appKey);
        param.put("gid",1451359325);
        String res = httpAPIService.doPost(url,param);
        System.out.println(res);
    }

}
