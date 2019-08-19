package com.lw.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.lw.common.Constants;
import com.lw.interceptor.LoginInterceptor;
/**
 * @author liwen
 *			
 *	*拦截器配置类
 */
@Configuration
public class WebConfig implements WebMvcConfigurer{
	/**
	 *  *注册LoginInterceptor
	 */
	
	@Autowired
	private LoginInterceptor loginInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	
		registry.addInterceptor(loginInterceptor)
				.excludePathPatterns(Constants.EXCLUDE_PATHS) 	//放行地址
				.addPathPatterns("/**");                        //拦截所有请求
		
		
	}
	
	

}
