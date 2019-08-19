package com.lw.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lw.entity.dto.MerchantUser;
import com.lw.mapper.MerchantUserMapper;

@Service
public class MerchantUserService extends ServiceImpl<MerchantUserMapper, MerchantUser>{
	
	/**
	 * 	通过用户名返回用户信息
	 * @param username
	 * @return
	 */
	@Transactional(readOnly = true)
	public MerchantUser getByUserName(String username) {
		
		QueryWrapper<MerchantUser> wrapper = new QueryWrapper<MerchantUser>();
		
		wrapper.lambda()
				.eq(MerchantUser::getUserName, username);   //user_name = #{username}
		
		return this.getOne(wrapper);
	}

}
