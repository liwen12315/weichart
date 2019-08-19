package com.lw.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lw.common.Constants;
import com.lw.common.Result;
import com.lw.entity.dto.Admin;
import com.lw.entity.dto.AdminModule;
import com.lw.entity.dto.AdminRole;
import com.lw.service.impl.AdminModuleRoleService;
import com.lw.service.impl.AdminRoleService;

@Controller
@RequestMapping("permission")
public class PerimissionController {
		
	@Autowired
	private AdminRoleService adminRoleService;
	
	@Autowired
	private AdminModuleRoleService adminModuleRoleService;
	
	
	
	/**
	 *  权限列表首页
	 */
	@RequestMapping("list")
	protected String showList(HttpServletRequest request,
							@RequestParam(value = "query",required = false)String query) {
		//获取当前用户的RoleID
		Admin admin = (Admin) request.getSession().getAttribute(Constants.CURRENT_USER);
		//获取比自己权限小的用户的列表
		AdminRole adminRole = adminRoleService.getById(admin.getRoleId());
		List<AdminRole> adminRoleList = adminRoleService.getByAdmin(adminRole);
		request.setAttribute("adminRole", adminRole);
		request.setAttribute("adminRoleList", adminRoleList);
		//获取当前用户的的权限列表
		List<AdminModule> adminModuleList = adminModuleRoleService.getByRoleId(admin.getRoleId());
		request.setAttribute("adminModuleList", adminModuleList);
		return "permission/list";
	}
	
	/**
	 *  *权限列表更新 数据展示接口
	 *  * 以ID获得用户的权限集合
	 */
	public class Permission{
		
		private AdminRole adminRole;
		
		private List<AdminModule> adminModulesList;

		public AdminRole getAdminRole() {
			return adminRole;
		}

		public void setAdminRole(AdminRole adminRole) {
			this.adminRole = adminRole;
		}

		public List<AdminModule> getAdminModulesList() {
			return adminModulesList;
		}

		public void setAdminModulesList(List<AdminModule> adminModulesList) {
			this.adminModulesList = adminModulesList;
		}

		public Permission() {
		
		}

		public Permission(AdminRole adminRole, List<AdminModule> adminModulesList) {
			this.adminRole = adminRole;
			this.adminModulesList = adminModulesList;
		}
	
	}
	
	@RequestMapping("getByRoleId")
	@ResponseBody
	protected Result<?> getById(HttpServletRequest request,
			@RequestParam(value = "id",required = true)Long id){
		
		//通过ROLE id 获得admin_role
		AdminRole adminRole = adminRoleService.getById(id);
		//获取要更改用户的权限列表
		List<AdminModule> adminModulesList = adminModuleRoleService.getByRoleId(id);
		
		return Result.success(new Permission(adminRole,adminModulesList));
	}
	
	/**
	 *  *更新或新增者删除admin_role
	 */
	@RequestMapping("save")
	@ResponseBody
	protected Result<?> saveUpdate(AdminRole adminRole,String moduleRoles){
		
		adminRoleService.saveAdmin(adminRole,moduleRoles);
		
		return Result.success();
	}
	
	
	/**
	 *  通过ID删除权限
	 * @param roleId
	 * @return
	 */
	@RequestMapping("del")
	@ResponseBody
	protected Result<?> del(@RequestParam(value = "id",required = true)Long id){
		
		adminRoleService.delete(id);
		
		return Result.success();
	}
	
	
}
