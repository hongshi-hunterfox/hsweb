package com.duobao.web.user.test;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.uclee.user.util.JwtUtil;

public class DateTest extends AbstractServiceTests{
	private static final Logger logger = Logger.getLogger(DateTest.class);
	@Test
	public void test(){
		logger.info(JSON.toJSONString(JwtUtil.genToken(1)));
	}
}