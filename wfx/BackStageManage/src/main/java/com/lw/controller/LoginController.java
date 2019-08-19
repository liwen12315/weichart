package com.lw.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lw.common.Constants;
import com.lw.common.ResultCode;
import com.lw.entity.dto.Admin;
import com.lw.service.impl.AdminService;


@Controller
public class LoginController {
	
	
	@Autowired
	private AdminService adminService;
	
	
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
				Admin admin = adminService.getByUserName(username);
				request.getSession().setAttribute(Constants.CURRENT_USER, admin);			
				return "forward:index";
		} catch (Exception e) {
			request.setAttribute("username", username);
			request.setAttribute("error", ResultCode.LOGIN_ERROR.getDescription());
			return "login";
		}
		
		
		
	}
	
	/**
	 *  * 更改管理员密码
	 */
	@RequestMapping("change")
	@ResponseBody
	protected String changePassword(@RequestParam("u")String u,@RequestParam("p")String p) {
		String hashAlgorithName = "MD5";
        String password = p;
        int hashIterations = 1024;//加密次数
        ByteSource credentialsSalt = ByteSource.Util.bytes(u); //加盐
        Object obj = new SimpleHash(hashAlgorithName, password,credentialsSalt,hashIterations);
     
        adminService.updatePassword(u,obj.toString());
        System.out.println(obj);
        return "OK";
	}

}
