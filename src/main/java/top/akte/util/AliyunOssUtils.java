package top.akte.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.BucketInfo;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * 阿里云 对象存储服务OSS 工具类
 * Created by zyj on 2017/9/6.
 */
//@Component
@Slf4j
public class AliyunOssUtils {

    @Value("${aliyunOss.endpoint}")
    private String endpoint;

    @Value("${aliyunOss.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyunOss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyunOss.bucketName}")
    private String bucketName;


    //富文本中的文件
    private String richPrefix = "rich";

    private String otherPrefix = "other";

    @PostConstruct
    public void init () {
        log.info("init oss config  endpoint:{},accessKeyId:{},accessKeySecret:{} ",endpoint,accessKeyId,accessKeySecret);
        OSSClient ossClient = getOSSClient();
        if (!ossClient.doesBucketExist(bucketName)){
            throw new RuntimeException("阿里云bucket:" + bucketName+ "不存在,请检查配置");
        }
        BucketInfo info = ossClient.getBucketInfo(bucketName);
        log.info("Bucket " + bucketName + "的信息如下：");
        log.info("数据中心：" + info.getBucket().getLocation());
        log.info("创建时间：" + info.getBucket().getCreationDate());
        log.info("用户标志：" + info.getBucket().getOwner());
        ossClient.shutdown();
    }

    public PutObjectResult putObject(String key, File file) {
        OSSClient ossClient = getOSSClient();
        log.info("put Object:{}", key);
        try {
            return ossClient.putObject(bucketName, key, file);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }



    public PutObjectResult putObject(String key, InputStream  inputStream) {
        OSSClient ossClient = getOSSClient();
        log.info("put Object:{}", key);
        try {
            return ossClient.putObject(bucketName, key, inputStream);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    public void deleteObject(String key) {
        OSSClient ossClient = getOSSClient();
        log.info("delete Object:{}", key);
        try {
            ossClient.deleteObject(bucketName, key);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    public OSSObject getObject(String key) {
        OSSClient ossClient = getOSSClient();
        try {
            return ossClient.getObject(bucketName, key);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    private OSSClient getOSSClient() {
        return new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    public String getDownloadUrlPrefix() {
        //获取Bucket的访问域名——Bucket名称.EndPoint。
        return endpoint.replace("https://", "https://" + bucketName + ".")+"/";
    }

    public String generatePresignedUrl(String key) {
        // 设置URL过期时间为10年  3600l* 1000*24*365*10
      /*  Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
        if (url != null) {
            return url.toString();
        }
        return null;*/
        return getDownloadUrlPrefix() + key;
    }

    private String getFinalFileName(String fileName) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        return uuid + suffix;
    //    return fileName;
    }



    public String getFinalKey(String prefix, String fileName) {
        return geKey(prefix,fileName);
    }

    public String getRichKey(String fileName) {
        return geKey(richPrefix,fileName);
    }

    public String getOtherKey(String fileName) {
        return geKey(otherPrefix,fileName);
    }
    private String geKey( String prefix,String fileName) {
        return prefix + getFolderDir() + getFinalFileName(fileName);
    }

    public String getExcelKey(String prefix, String fileName) {
        return prefix + getFolderDir() + "/" + UUID.randomUUID().toString().replace("-", "") + "/" + fileName;
    }

    private String getFolderDir() {
      return DateUtils.formatDate(new Date(), "/yyyy/MM/dd/HH/mm/");
    }

}
