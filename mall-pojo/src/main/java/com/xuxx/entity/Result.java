package com.xuxx.entity;

import java.io.Serializable;
/**
 * 返回结果
 * @author Administrator
 *
 */
public class Result implements Serializable{

	private boolean success;//是否成功
	
	private String message;//返回信息
	
	private Object data;
	
	public Result(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}
	
	public static Result buildSuccessResult(String message) {
		return new Result(true, message);
	}
	
	public static Result buildFailResult(String message) {
		return new Result(false, message);
	}

	public boolean isSuccess() {
		return success;
	}

	public Result setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public Result setMessage(String message) {
		this.message = message;
		return this;
	}

	public Object getData() {
		return data;
	}

	public Result setData(Object data) {
		this.data = data;
		return this;
	}
}
