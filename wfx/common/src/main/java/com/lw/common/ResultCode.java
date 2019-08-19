package com.lw.common;

/**
 * 	返回结果枚举类
 * @author liwen
 *
 */

public enum ResultCode {
	
	SUCCESS(0,true,"成功"),                    //逻辑成功   
	
	ERROR(1,false,"失败"),					 //逻辑失败
		
	LOGIN_SUCCESS(2,true,"登陆成功"),			 //登陆成功
	
	LOGIN_ERROR(3,false,"账号或密码有误");			//登陆失败
	
	
	private int code = 0;							//状态编码
	private Boolean ok = null;						//逻辑成功与否
	private String description = null;				//描述
	
	
	public int getCode() {
		return code;
	}
	
	public Boolean getOk() {
		return ok;
	}
	
	public String getDescription() {
		return description;
	}

	private ResultCode(int code, Boolean ok, String description){
		this.code = code;
		this.ok = ok;
		this.description = description;
	}
	
	private ResultCode(){
		
	}
	

	/**
	 *  *通过状态编码获取ResultCode
	 */
	public static ResultCode get(int code) {
		//遍历枚举项
		for(ResultCode result:ResultCode.values()) {
			//当满足code 匹配成功是就返回当前 ResultCode
			if(result.getCode()==code) {
				return result;
			}			
		}	
		return null;
	}
	
	/**
	 *  * 通过描述获取ReslutCode
	 */
	public static ResultCode get(String description) {
		//遍历枚举项
		for(ResultCode result:ResultCode.values()) {
			//当满足description 匹配成功是就返回当前 ResultCode
			if(result.getDescription()==description) {
				return result;
			}			
		}	
		return null;
	}

}
