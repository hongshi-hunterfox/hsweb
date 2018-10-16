package com.uclee.fundation.oss;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class DfsInit {
	
	public static String protocal = "http://";
	public static String endpoint;
	public static String accessKeyId;
	public static String accessKeySecret;
	public static String bucketName;
	public static String storePath;
	public static final String fileSeparator = "/";
	
	public static void init(String classPath) {
		
		Properties p = new Properties();

		try {
			InputStream inputStream = DfsInit.class.getClassLoader().getResourceAsStream(
					classPath);

			p.load(inputStream);
			
			endpoint = (String)p.get("endpoint");
			accessKeyId = (String)p.get("accessKeyId");
			accessKeySecret = (String)p.get("accessKeySecret");
			bucketName = (String)p.get("bucketName");
			storePath = (String)p.get("storePath");
			protocal = (String)p.get("protocal");

		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	

}
