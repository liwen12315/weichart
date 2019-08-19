package com.lw.shiro;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.lw.entity.dto.Admin;
import com.lw.service.impl.AdminService;

/**
 *	shiRo的realm,数据交互的桥梁
 */
public class AuthRealm extends AuthorizingRealm{
	
	@Autowired
	private AdminService adminService;
	
	
	/**
	 *   *权限验证 使用方法在需要权限的方法前加注解
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		 SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		 //设置权限集合
		 Set<String> permissionSet = new HashSet<>();
		 //添加需要权限执行的操作
		 permissionSet.add("");
		 simpleAuthorizationInfo.setStringPermissions(permissionSet);
		 
		 return simpleAuthorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//强转为账号密码类型的token
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)token;
		
		String userName = usernamePasswordToken.getUsername();
		
		Admin admin = adminService.getByUserName(userName);
		//当前realm对象的name
        String realmName = getName();
		// 当账号为空的时候
		if(admin == null) {
			throw new RuntimeException("账号或者密码错误");
		}
		
		//盐值
        ByteSource credentialsSalt = ByteSource.Util.bytes(admin.getAccount());
	
		return new SimpleAuthenticationInfo(admin,admin.getPassword(),credentialsSalt,realmName);
				
	}

	

}
