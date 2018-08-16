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
import top.akte.entity.vo.OpenIdVo;
import top.akte.request.common.WxRequest;
import top.akte.request.customer.GetOpenIdWxReq;
import top.akte.response.common.ResponseCodeConstant;
import top.akte.response.common.WxResponse;
import top.akte.service.wx.CustomerWxService;

import java.io.IOException;

@Api(description = "wx-客户模块")
@RestController
@RequestMapping("/api/wx/customer")
@Log4j
public class WxCustomerController {

    @Autowired
    private CustomerWxService customerWxService;

    @ApiOperation(value = "从当前session获取openId", httpMethod = "POST")
    @ApiImplicitParams({
    })
    @RequestMapping(value = {"checkOpenId"},method = RequestMethod.POST)
    public WxResponse<String> checkOpenId(@RequestBody WxRequest request){
        WxResponse response = new WxResponse();
        response.setOpenId(request.getOpenId());
        response.setCode(ResponseCodeConstant.SUCCESS.getResponseCode());
        response.setContent(ResponseCodeConstant.SUCCESS.getResponseDesc());
        return response;
    }

    @ApiOperation(value = "获取openId", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value="登录时获取的 code", paramType = "query"),
    })
    @RequestMapping(value = {"getOpenId"},method = RequestMethod.POST)
    public WxResponse<OpenIdVo> getOpenId(@RequestBody GetOpenIdWxReq req) throws IOException{
        return customerWxService.getOpenId(req);
    }



}
