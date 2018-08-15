package top.akte.controller.wx;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.akte.entity.vo.AdContentVo;
import top.akte.request.ad.AdListWxRequest;
import top.akte.response.common.PageRes;
import top.akte.response.common.WxResponse;
import top.akte.service.wx.AdContentWxService;

@Api(description = "wx-广告模块")
@RestController
@RequestMapping("/api/wx/ad")
@Log4j
public class WxAdController {

    @Autowired
    private AdContentWxService adContentWxService;

    @ApiOperation(value = "广告列表", notes = "广告列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adModuleId", value="模块id", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value="页码", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value="每页大小", paramType = "query")
    })
    @RequestMapping(value = {"adList"},method = RequestMethod.POST)
    public WxResponse<PageRes<AdContentVo>> adList(@RequestBody AdListWxRequest req){
        return adContentWxService.adList(req);
    }

}
