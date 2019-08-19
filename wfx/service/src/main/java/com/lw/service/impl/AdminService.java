package com.lw.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lw.entity.dto.Admin;
import com.lw.mapper.AdminMapper;

@Service
public class AdminService extends ServiceImpl<AdminMapper, Admin>{
	
	/**
	 *  *通过用户名得到账户，用于ShiRo验证
	 */
	public Admin getByUserName(String userName) {
		
		QueryWrapper<Admin> wrapper = new QueryWrapper<Admin>();
		wrapper.lambda()
				.eq(Admin::getAccount,userName);     //查找account = #{userName}
		
		return this.getOne(wrapper);
	}
	
	/**
	 * 	升级密码账号
	 */
	public void updatePassword(String u,String string) {
		UpdateWrapper<Admin> wrapper = new UpdateWrapper<Admin>();
		wrapper.lambda()
				.eq(Admin::getAccount,u)
				.set(Admin::getPassword, string);
		this.update(wrapper);		
	}
	

}
