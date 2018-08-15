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
import top.akte.request.jtt.JttGoodsDetailWxReq;
import top.akte.request.jtt.JttGoodsListWxReq;
import top.akte.response.common.PageRes;
import top.akte.response.common.WxResponse;
import top.akte.response.jtt.JttGoodsDetailVo;
import top.akte.response.jtt.JttGoodsItemVo;
import top.akte.service.wx.JttGoodsWxService;

import java.io.IOException;

@Api(description = "wx-商品模块")
@RestController
@RequestMapping("/api/wx/goods")
@Log4j
public class WxGoodsController {

    @Autowired
    private JttGoodsWxService jttGoodsWxService;

    @ApiOperation(value = "商品列表", notes = "商品列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value="商品类型：1 : 女装 2 : 男装3 : 内衣配饰 4 : 母婴玩具 5 : 美妆个护 6 : 食品保健 7 : 居家生活 8 : 鞋品箱包 9 : 运动户外 10 : 文体车品 11 : 数码家电", paramType = "query"),
            @ApiImplicitParam(name = "rank", value="商品筛选： self : 京东自营 sift : 精选好货； wtype : 京东配送产品； finally : 券后价最低； brokerage : 佣金比例最高； sale : 优惠最多；", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value="页码", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value="每页大小", paramType = "query")
    })
    @RequestMapping(value = {"goodsList"},method = RequestMethod.POST)
    public WxResponse<PageRes<JttGoodsItemVo>> goodsList(@RequestBody JttGoodsListWxReq req) throws Exception {
        return jttGoodsWxService.goodsList(req);
    }

    @ApiOperation(value = "商品详情", notes = "商品详情", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gid", value="商品id", paramType = "query"),
    })
    @RequestMapping(value = {"goodsDetail"},method = RequestMethod.POST)
    public WxResponse<JttGoodsDetailVo> goodsDetail(@RequestBody JttGoodsDetailWxReq req) throws Exception {
        return jttGoodsWxService.goodsDetail(req);
    }

}
