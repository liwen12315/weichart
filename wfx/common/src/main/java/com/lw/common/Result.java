package com.lw.common;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author liwen
 * 	 统一返回类
 */
@JsonSerialize
@JsonInclude(value = Include.NON_NULL)
public class Result<T> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int code;                   //结果编号
	private Boolean ok;					 //逻辑成功与否
	private String description;			 //执行结果描述
	private T data; 					 //返回数据
	
	/**
	 *  *获取状态编码
	 * @return
	 */
	public int getCode() {
		return code;
	}
	
	/**
	 *   *获取逻辑执行结果
	 */
	public Boolean getOk() {
		return ok;
	}
	
	/**
	 *  *获取描述
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 *  *获取数据
	 */
	public T getData() {
		return data;
	}
	
	/**
	 *  *设置返回数据
	 */
	public void setData(T data) {
		this.data = data;
	}

	public Result(int code, Boolean ok, String description, T data) {
		this.code = code;
		this.ok = ok;
		this.description = description;
		this.data = data;
	}
	

	public Result(int code, Boolean ok, String description) {
		this.code = code;
		this.ok = ok;
		this.description = description;
	}

	public Result() {

	}
	
	
	
	/**
	 *   *设置Result
	 */
	
	public static <T> Result<T> create(ResultCode resultCode) {
        return new Result<T>(resultCode.getCode(), resultCode.getOk(), resultCode.getDescription());
    }
	

	public static <T> Result<T> create(ResultCode resultCode,T data) {
        return new Result<T>(resultCode.getCode(), resultCode.getOk(), resultCode.getDescription(), data);
    }
	
	/**
	 *  *设置成功信息
	 */
	
	public static <T> Result<T>  success(T data){
		return new Result<T>(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getOk(),ResultCode.SUCCESS.getDescription(),data);
	}
	
	

	public static <T> Result<T>  success(){
		return new Result<T>(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getOk(),ResultCode.SUCCESS.getDescription());
	}
	
	
	/**
	 *  *设置返回失败信息
	 */
	public static <T> Result<T>  error(T data){
		return new Result<T>(ResultCode.ERROR.getCode(),ResultCode.ERROR.getOk(),ResultCode.ERROR.getDescription(),data);
	}
	

	public static <T> Result<T>  error(){
		return new Result<T>(ResultCode.ERROR.getCode(),ResultCode.ERROR.getOk(),ResultCode.ERROR.getDescription());
	}
	
	
	
	
	
	
	
	
	
	
	
}
