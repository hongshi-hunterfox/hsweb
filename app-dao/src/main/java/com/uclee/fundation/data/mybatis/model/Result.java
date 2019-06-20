package com.uclee.fundation.data.mybatis.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

@Data
public class Result implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer state = 0;
	private String msg = "成功";
	private Object data;


	public static Result assembly() {

		return new Result();
	}

	public static Result assembly(Object data) {
		Result result = new Result();
		result.data = data;
		return result;
	}

	public static Result assembly(Integer state, String msg, Object data) {
		Result result = new Result();
		result.state = state;
		result.msg = msg;
		result.data = data;
		return result;
	}

	@Override
	public String toString() {
		Map<String, Object> map = new HashMap<>();
		map.put("state", state);
		map.put("msg", msg);
		map.put("data", data);
		return JSONObject.toJSONString(map);
	}
}
