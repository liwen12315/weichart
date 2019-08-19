package com.lw.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lw.common.Constants;
import com.lw.common.Result;
import com.lw.entity.dto.Admin;
import com.lw.entity.vo.TreeView;
import com.lw.service.impl.AdminModuleRoleService;


/**
 *   *首页内容
 * @author liwen
 *
 */
@Controller
public class IndexController {
	
	@Autowired
	private AdminModuleRoleService adminModuleRoleService;
	
	/**
	 * 	*跳转首页
	 */
	@RequestMapping("index")
	protected String goIndex() {
		return "index";
	}
	
	/**
	 *  *首页左边导航树（功能树）
	 */
	@RequestMapping("fun-list")
	@ResponseBody
	protected Result<?> showView(HttpServletRequest request){
		
		Admin admin = (Admin) request.getSession().getAttribute(Constants.CURRENT_USER);
		Long id = admin.getRoleId();
		List<TreeView> list = adminModuleRoleService.geTreeViews(id);
		
		return Result.success(list); 
	}
	
	
	

}
