import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.akte.WxApplication;
import top.akte.request.ad.AdListWxRequest;
import top.akte.service.wx.AdContentWxService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WxApplication.class)
public class AdTest {
    @Autowired
    private AdContentWxService adContentWxService;

    @Test
    public void adList(){
        AdListWxRequest req = new AdListWxRequest();
        req.setAdModuleId("aa");
        req.setPageNo(1);
        req.setPageSize(10);
        System.out.println(adContentWxService.adList(req));
    }
}
