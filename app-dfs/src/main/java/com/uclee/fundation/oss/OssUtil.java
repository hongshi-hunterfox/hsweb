package com.uclee.fundation.oss;


import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
  
//阿里云对象存储服务OSS工具  
@Component
public class OssUtil {  
  
  
    // 演示，创建Bucket的时候，endpoint不能带上.  
    // 图片上传和简单的图片访问也可以用这个。

    @Value(value="${oss.endpoint:http://oss-cn-shenzhen.aliyuncs.com}")
    public String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
  
  
    // 图片处理，需要用单独的地址。访问、裁剪、缩放、效果、水印、格式转换等服务。  
    // public String endpointImg = "http://img-cn-hangzhou.aliyuncs.com";  


    @Value(value="${oss.accessKeyId:LTAIb36ti4sJYhwY}")
    public String accessKeyId = "LTAIb36ti4sJYhwY";

    @Value(value="${oss.accessKeySecret:wDkzuBidUH6oog7jvdxW9A4JNS42br}")
    public String accessKeySecret;

    @Value(value = "${oss.backetName:wschs}")
    public String bucketName;
    
    @Value(value = "${oss.url.prefix:http://wsc.in80s.com/file/}")
    public String ossUrlPrefix;
  
  
    // 单例，只需要建立一次链接  
    private OSSClient client = null;  
    // 是否使用另外一套本地账户  
    public final boolean MINE = false;  
  
  
    {  
        if (MINE) {  
            accessKeyId = "LTAIb36ti4sJYhwY";
            accessKeySecret = "wDkzuBidUH6oog7jvdxW9A4JNS42br";
            bucketName = "wschs";
            endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
        }  
    }  
  
  
    //配置参数  
    ClientConfiguration config() {  
        ClientConfiguration conf = new ClientConfiguration();  
        conf.setMaxConnections(100);  
        conf.setConnectionTimeout(5000);  
        conf.setMaxErrorRetry(3);  
        conf.setSocketTimeout(2000);  
        return conf;  
    }  
  
  
    //客户端  
    public OSSClient client() {  
        if (client == null) {  
            ClientConfiguration conf = config();  
            client = new OSSClient(endpoint, accessKeyId, accessKeySecret, conf);  
            makeBucket(client, bucketName);  
        }  
        return client;  
    }  
  
  
    //创建Bucket  
    public void makeBucket(String bucketName) {  
        OSSClient client = client();  
        makeBucket(client, bucketName);  
    }  
  
  
    //创建Bucket  
    public void makeBucket(OSSClient client, String bucketName) {  
        boolean exist = client.doesBucketExist(bucketName);  
        if (exist) {  
            p("The bucket exist.");  
            return;  
        }  
        client.createBucket(bucketName);  
    }  
  
  
    /**
     * 上传一个文件，InputStream  
     * @param is
     * @param key
     */
    public String uploadFile(InputStream is, String key) {  
        OSSClient client = client();  
        PutObjectRequest putObjectRequest = new PutObjectRequest(  
                bucketName, key, is);  
        client.putObject(putObjectRequest);  
        return ossUrlPrefix+key;
    }  
  
  
    /**
     * 上传一个文件，File  
     * @param file
     * @param key
     */
    public String uploadFile(File file, String key) {  
        OSSClient client = client();  
        PutObjectRequest putObjectRequest = new PutObjectRequest(  
                bucketName, key, file);  
        client.putObject(putObjectRequest);  
        return ossUrlPrefix+key;
    }  
  
    /**
     * 上传一个文件，String类型，转为文件
     * @param str
     * @param key
     */
    public String uploadFile(String str, String key) {  
    	OSSClient client = client();  
    	PutObjectRequest putObjectRequest = new PutObjectRequest(  
    			bucketName, key,  new ByteArrayInputStream(str.getBytes()));  
    	client.putObject(putObjectRequest);  
    	return ossUrlPrefix+key;
    }  
    
    /**
     * 上传一个文件，MultipartFile  
     * @param file
     * @param key
     */
    public String uploadFile(MultipartFile file, String key) {  
    	try {
			uploadFile(file.getInputStream(),key);
			return ossUrlPrefix+key;
		} catch (IOException e) {
			e.printStackTrace();
		} 
    	return null;
    }  
    
  
    //下载一个文件到本地  
    public OSSObject downloadFile(String key) {  
        OSSClient client = client();  
        GetObjectRequest getObjectRequest = new GetObjectRequest(  
                bucketName, key);  
        OSSObject object = client.getObject(getObjectRequest);  
        return object;  
    }  
  
  

  

    //创建目录，不能以斜杠“/”开头  
    public void makeDir(String keySuffixWithSlash) {  
        OSSClient client = client();  
        /* 
         * Create an empty folder without request body, note that the key must 
         * be suffixed with a slash 
         */  
        if (StringUtils.isEmpty(keySuffixWithSlash)) {  
            return;  
        }  
        if (!keySuffixWithSlash.endsWith("/")) {  
            keySuffixWithSlash += "/";  
        }  
        client.putObject(bucketName, keySuffixWithSlash,  
                new ByteArrayInputStream(new byte[0]));  
    }  
  
  

  

  
  
  
  
    public void p(Object str) {  
        System.out.println(str);  
    }  
  
  
    public void print(OSSException oe) {  
        p("Caught an OSSException, which means your request made it to OSS, "  
                + "but was rejected with an error response for some reason.");  
        p("Error Message: " + oe.getErrorCode());  
        p("Error Code:       " + oe.getErrorCode());  
        p("Request ID:      " + oe.getRequestId());  
        p("Host ID:           " + oe.getHostId());  
    }  
}  

