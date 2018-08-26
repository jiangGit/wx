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
import top.akte.request.common.WxRequest;
import top.akte.request.pdd.*;
import top.akte.response.common.PageRes;
import top.akte.response.common.WxResponse;
import top.akte.response.pdd.*;
import top.akte.service.wx.PddWxService;

import java.io.IOException;
import java.util.List;

@Api(description = "拼多多模块")
@RestController
@RequestMapping("/api/wx/pdd")
@Log4j
public class WxPddController {

    @Autowired
    private PddWxService pddWxService;

    @ApiOperation(value = "查询类别列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentCatId", value="父级ID", paramType = "query"),
    })
    @RequestMapping(value = {"catList"},method = RequestMethod.POST)
    public WxResponse<List<PddGoodsCatItemVo>> catList(@RequestBody PddCatListWxReq req)throws IOException {
        return  pddWxService.catList(req);
    }

    @ApiOperation(value = "查询标签列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentOptId", value="父级ID", paramType = "query"),
    })
    @RequestMapping(value = {"optList"},method = RequestMethod.POST)
    public WxResponse<List<PddGoodsOptItemVo>> optList(@RequestBody PddOptListWxReq req)throws IOException{
        return pddWxService.optList(req);
    }

    @ApiOperation(value = "商品列表查询", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "optId", value="商品标签类目ID", paramType = "query"),
    })
    @RequestMapping(value = {"searchGoods"},method = RequestMethod.POST)
    public WxResponse<PageRes<PddGoodsItemVo>> searchGoods(@RequestBody PddSearchGoodsWxReq req) throws IOException {
        return pddWxService.searchGoods(req);
    }

    @ApiOperation(value = "商品详情", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value="商品id", paramType = "query"),
    })
    @RequestMapping(value = {"goodsDetail"},method = RequestMethod.POST)
    public WxResponse<PddGoodsDetailVo> goodsDetail(@RequestBody PddGoodsDetailWxReq req) throws IOException {
        return pddWxService.goodsDetail(req);
    }

    @ApiOperation(value = "生成推广地址", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value="商品id", paramType = "query"),
            @ApiImplicitParam(name = "pid", value="广告位id？后面考虑去掉", paramType = "query"),
    })
    @RequestMapping(value = {"genQrcode"},method = RequestMethod.POST)
    public WxResponse<String > genQrcode(@RequestBody PddGenQrcodeWxReq req)throws IOException{
        return pddWxService.genQrcode(req);
    }

    @ApiOperation(value = "查询已经生成的推广位信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gid", value="商品id", paramType = "query"),
    })
    @RequestMapping(value = {"queryPid"},method = RequestMethod.POST)
    public WxResponse<PageRes<PddPItemVo>> queryPid(@RequestBody PddQueryPidWxReq req) throws IOException {
        return pddWxService.queryPid(req);
    }

    @ApiOperation(value = "生成推广广告位", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gid", value="商品id", paramType = "query"),
    })
    @RequestMapping(value = {"generatePid"},method = RequestMethod.POST)
    public WxResponse generatePid(@RequestBody WxRequest req) throws IOException {
        return pddWxService.generatePid(req);
    }

    @ApiOperation(value = "生成推广链接", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gid", value="商品id", paramType = "query"),
    })
    @RequestMapping(value = {"generateUrl"},method = RequestMethod.POST)
    public WxResponse<PddGenUrlVo> generateUrl(@RequestBody PddGenerateUrlWxReq req) throws IOException{
        return pddWxService.generateUrl(req);
    }

}

