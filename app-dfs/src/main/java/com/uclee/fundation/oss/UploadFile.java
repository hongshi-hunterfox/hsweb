package com.uclee.fundation.oss;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service("uploadFile")
public class UploadFile {

    private static final Logger logger = LoggerFactory.getLogger(UploadFile.class);

    static {
        try {
            String classPath = new File(UploadFile.class.getResource("/").getFile()).getCanonicalPath() + File.separator + FileConfig.OSS_CONF_FILE;
            logger.info("=== CONF_FILENAME:" + classPath);
            DfsInit.init(FileConfig.OSS_CONF_FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 删除文件
     *
     * @param file_id
     * @return
     * @throws IOException
     */
    public int deleteFile(String file_id) throws IOException {
        int result = 0;
        try {
        	OSSClient ossClient = new OSSClient(DfsInit.protocal + DfsInit.endpoint, DfsInit.accessKeyId, DfsInit.accessKeySecret);
        	ossClient.deleteObject(DfsInit.bucketName, file_id);
        } catch (Exception e) {
            logger.warn("delete file \"" + file_id + "\"fails");
        }

        return result;
    }

    /**
     * 上传文件流
     * Upload File to OSS.
     */
    public String uploadFile(InputStream inStream, String uploadFileName,
                             long fileLength) throws Exception {
        String fileId = "";
        String fileExtName = "";
        if (uploadFileName.contains(".")) {
            fileExtName = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);
        } else {
            logger.warn("Fail to upload file, because the format of filename is illegal. uploadFileName = ", uploadFileName);
            return fileId;
        }
        
        PutObjectResult putObjectResult = null;
        
        String key = generateKey(uploadFileName);
        
        String location = getLocaton(key);
        
        System.out.println(location);

        // 上传文件
        try {
        	
            OSSClient ossClient = new OSSClient(DfsInit.protocal + DfsInit.endpoint, DfsInit.accessKeyId, DfsInit.accessKeySecret);
            
            try {
                putObjectResult = ossClient.putObject(DfsInit.bucketName, key, inStream);
                System.out.println(putObjectResult.getETag());
                
            } catch (OSSException oe) {
                System.out.println("Caught an OSSException, which means your request made it to OSS, "
                        + "but was rejected with an error response for some reason.");
                System.out.println("Error Message: " + oe.getErrorCode());
                System.out.println("Error Code:       " + oe.getErrorCode());
                System.out.println("Request ID:      " + oe.getRequestId());
                System.out.println("Host ID:           " + oe.getHostId());
            } catch (ClientException ce) {
                System.out.println("Caught an ClientException, which means the client encountered "
                        + "a serious internal problem while trying to communicate with OSS, "
                        + "such as not being able to access the network.");
                System.out.println("Error Message: " + ce.getMessage());
            } catch (Throwable e) {
                e.printStackTrace();
            } finally {
                ossClient.shutdown();
            }
        	
        }  finally {

            try {
                inStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        if(putObjectResult != null) {
        	fileId = key;
        	logger.info("Upload Success fileId: {}, fileLength{}", fileId, fileLength);
        }
        
        return fileId;
    }

	private String generateKey(String uploadFileName) {
		String uuid = RandomStringUtil.getUUID();
		int hashCode = uuid.hashCode();
		int dir1 = hashCode & 0xf;
		int dir2 = (hashCode & 0xf0) >> 4;
		
		String dirString1 = "" + dir1;
		String dirString2 = "" + dir2;
		
		if(dir1 < 10) dirString1 = "0" + dirString1;
			
		if(dir2 < 10) dirString2 = "0"+ dirString2;

		String relativePath = DfsInit.storePath + DfsInit.fileSeparator + dirString1 + DfsInit.fileSeparator + dirString2;
		
		String key = relativePath + DfsInit.fileSeparator + uuid + uploadFileName.substring(uploadFileName.lastIndexOf("."), uploadFileName.length());
		return key;
	}

	private String getLocaton(String key) {
		return DfsInit.protocal + DfsInit.bucketName + "." + DfsInit.endpoint + DfsInit.fileSeparator + key;
	}


}
