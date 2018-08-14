package top.akte.controller.common;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.akte.response.common.CommonResponse;
import top.akte.response.common.ResponseCodeConstant;
import top.akte.response.common.FileResponse;
import top.akte.util.AliyunOssUtils;

import java.io.IOException;

@Api(description = "公共模块")
@RestController
@RequestMapping("/api/common")
@Log4j
public class CommonController {
/*
    @Autowired
    private AliyunOssUtils aliyunOssUtils;

    @ApiOperation(value = "上传", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value="上传文件", paramType = "query"),
    })
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public CommonResponse<FileResponse> upload(@RequestParam("file") MultipartFile imgFile) {
        CommonResponse<FileResponse> commonResponse = new CommonResponse();
        try {
            String key = aliyunOssUtils.getFinalKey("wx", imgFile.getOriginalFilename());
            aliyunOssUtils.putObject(key, imgFile.getInputStream());
            FileResponse fileResponse = new FileResponse();
            fileResponse.setKey(key);
            fileResponse.setUrl(aliyunOssUtils.generatePresignedUrl(key));
            commonResponse.setResult(fileResponse);
            commonResponse.setCode(ResponseCodeConstant.SUCCESS.getResponseCode());
            commonResponse.setContent(ResponseCodeConstant.SUCCESS.getResponseDesc());
        } catch (IOException e) {
            log.error("上传异常", e);
            commonResponse.setCode(ResponseCodeConstant.SYS_EXCEPTION.getResponseCode());
            commonResponse.setContent("上传异常");
        }
        return commonResponse;
    }
    */
}
