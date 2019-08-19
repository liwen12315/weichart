package com.lw.common;
/**
 * 	*公共常量类
 * @author liwen
 *
 */

import java.util.Arrays;
import java.util.List;


public class Constants {
	/**
	 *  *当前的用户
	 */
	public static final String CURRENT_USER = "CURRENT_USER";
	
	/**
	 *  * shiRo 排除过滤resources
	 */
	public static final List<String> EXCLUDE_PATHS = Arrays.asList("/", "/login", "/login.do", "/css/**", "/fonts/**", "/img/**", "/js/**", "/favicon.ico","/error","/callback/**","/change");
	
	/**
	 *  *分页页面大小
	 */
	public static final int PAGE_SIZE = 3;
	
}
