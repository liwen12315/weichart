package com.lw.controller;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lw.common.Constants;
import com.lw.common.Result;
import com.lw.entity.dto.MerchantUser;
import com.lw.service.impl.MerchantUserService;

/**
 * 
 */
@Controller
@RequestMapping("merchant")
public class MerchanUserController {
	
	@Autowired
	private MerchantUserService merchantUserService;
	
	/**
	 *  *首页展示用户列表
	 */
	@RequestMapping("list")
	protected String showList(HttpServletRequest request, 
			@RequestParam(value = "query",required = false)String query,
			@RequestParam(value = "pageId",required = false,defaultValue ="1")Integer pageId){
		
		QueryWrapper<MerchantUser> wrapper = new QueryWrapper<MerchantUser>();
		if(!"".equals(query) && query != null) {
			wrapper.lambda()
					.like(MerchantUser::getId, query).or()
					.like(MerchantUser::getName, query).or()
					.eq(MerchantUser::getPhone, query).or()
					.eq(MerchantUser::getQq, query).or()
					.eq(MerchantUser::getUserName,query);
		}
		wrapper.lambda().orderByAsc(MerchantUser::getCreateTime);
		IPage<MerchantUser> page = merchantUserService.page(new Page<>(pageId, Constants.PAGE_SIZE), wrapper);
		
        request.setAttribute("page", page);
		return "merchant/list";
	}
	
	/**
	 *  *修改用户信息 回显
	 */
	@RequestMapping("getById")
	@ResponseBody
	protected Result<?> getById(@RequestParam(value = "id",required = true)Long id){
		
		MerchantUser merchantUser = merchantUserService.getById(id);
		
		return Result.success(merchantUser);
	}
	
	/**
	 * 	*删除商户
	 */
	@RequestMapping("del")
	@ResponseBody
	protected Result<?> removeById(@RequestParam(value = "id",required = true)Long id){
		
		boolean flag= merchantUserService.removeById(id);
		
		return Result.success(flag);
	}
	
	
	
	/**
	 * 	* 更新或者插入操作
	 */
	@RequestMapping("save")
	@ResponseBody
	protected Result<?> saveUpdate(MerchantUser user){
		
		String hashAlgorithName = "MD5";
        String password = user.getPassword();
        int hashIterations = 1024;//加密次数
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getUserName()); //加盐
        Object obj = new SimpleHash(hashAlgorithName, password,credentialsSalt,hashIterations);
		
        user.setPassword(obj.toString());
		
		boolean flag = true;
		try {
			merchantUserService.saveOrUpdate(user);
			return Result.success(flag);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
		
		
	}
}
