package com.lw.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lw.common.Constants;
import com.lw.common.ResultCode;
import com.lw.entity.dto.MerchantUser;
import com.lw.service.impl.MerchantUserService;


@Controller
public class LoginController {
	
	
	
	@Autowired
	private MerchantUserService merchantUserService;
	
	@RequestMapping({"login","/"})
	protected String goLogin() {
		return "login";
	}
	
	/**
	 *   *登陆验证
	 * @param request
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("login.do")
	protected String login(HttpServletRequest request,@RequestParam("username")String username,
							@RequestParam("password")String password) {
		
		UsernamePasswordToken token = new UsernamePasswordToken(username,password);
		Subject subject = SecurityUtils.getSubject();
		
		try {
			subject.login(token);
				//通过用户名userName获取
				MerchantUser merchantUser = merchantUserService.getByUserName(username);
				request.getSession().setAttribute(Constants.CURRENT_USER, merchantUser);			
				return "forward:index";
		} catch (Exception e) {
			request.setAttribute("username", username);
			request.setAttribute("error", ResultCode.LOGIN_ERROR.getDescription());
			return "login";
		}	
	}
	

}
