package com.zhangzlyuyx.easy.shiro.authz;

import java.util.Collection;
import java.util.HashSet;

import javax.annotation.PostConstruct;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.Permission;

import com.zhangzlyuyx.easy.shiro.Constant;
import com.zhangzlyuyx.easy.shiro.ShiroPrincipal;
import com.zhangzlyuyx.easy.shiro.ShiroToken;
import com.zhangzlyuyx.easy.shiro.filter.AuthenticationFilter;
import com.zhangzlyuyx.easy.spring.util.SpringUtils;

/**
 * 简易认证处理器抽象类
 * @author zhangzlyuyx
 *
 */
public abstract class SimpleAuthenticationHandler implements AuthenticationHandler {
	
	/**
	 * 单例模式认证处理器
	 */
	private static SimpleAuthenticationHandler instance;
	
	/**
	 * 获取单例认证处理器
	 * @return
	 */
	public static synchronized SimpleAuthenticationHandler getInstance() {
		return instance;
	}
	
	/**
	 * 设置单例认证处理器
	 * @param instance
	 */
	public static synchronized void setInstance(SimpleAuthenticationHandler instance) {
		SimpleAuthenticationHandler.instance = instance;
	}
	
	public SimpleAuthenticationHandler() {
		
	}
	
	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		if(SimpleAuthenticationHandler.getInstance() == null) {
			SimpleAuthenticationHandler.setInstance(this);
		}
	}
	
	@Override
	public AuthenticationToken createToken(AuthenticationFilter authenticationFilter, AuthenticationToken token,
			ServletRequest request, ServletResponse response) {
		
		if(token instanceof ShiroToken) {
			ShiroToken shiroToken = (ShiroToken)token;
			shiroToken.getAttributes().put(Constant.SHIROTOKEN__ATTRIBUTE_USERAGENT, SpringUtils.getUserAgent((HttpServletRequest)request));
			shiroToken.getAttributes().put(Constant.SHIROTOKEN__ATTRIBUTE_CLIENTIP, SpringUtils.getClientIP((HttpServletRequest)request));
			shiroToken.getAttributes().put(Constant.SHIROTOKEN__ATTRIBUTE_URL, SpringUtils.getRequestUrl(request));
		}
		
		return token;
	}

	@Override
	public Object getPrincipal(AuthenticationToken token) {
		if(token instanceof ShiroToken) {
			((ShiroToken)token).setAuthenticationHandler(this);
		}
		return token.getPrincipal();
	}
	
	@Override
	public Object getCredentials(AuthenticationToken token) {
		return token.getCredentials();
	}
	
	@Override
	public Collection<String> getRoles(Object principal) {
		if(principal instanceof ShiroPrincipal) {
			return ((ShiroPrincipal)principal).getRoles();
		}
		return new HashSet<>();
	}

	@Override
	public Collection<String> getStringPermissions(Object principal) {
		if(principal instanceof ShiroPrincipal) {
			return ((ShiroPrincipal)principal).getPermissions();
		}
		return new HashSet<String>();
	}

	@Override
	public Collection<Permission> getObjectPermissions(Object principal) {
		return new HashSet<Permission>();
	}

	
}