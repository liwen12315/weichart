package com.lw.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.lw.common.Constants;

/** 
 * @author liwen
 *	 *拦截所有未登录的请求
 */
@Component
public class LoginInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//获取session中的用户 如果用户名为空 则是未登录的请求 直接返回登陆界面
		HttpSession session = request.getSession();
		
		if (session.getAttribute(Constants.CURRENT_USER) != null){
			System.out.println("haha:"+session.getAttribute(Constants.CURRENT_USER));
            return true;
        }
		
		System.out.println("未登陆的请求:"+request.getRequestURL());
		response.sendRedirect("/");
		
		return false;
	}
	
	

}
