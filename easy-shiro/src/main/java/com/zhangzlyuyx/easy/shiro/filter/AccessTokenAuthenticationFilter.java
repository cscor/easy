package com.zhangzlyuyx.easy.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;

import com.zhangzlyuyx.easy.shiro.Constant;
import com.zhangzlyuyx.easy.shiro.authc.AccessToken;

/**
 * access token 过滤器
 * @author zhangzlyuyx
 *
 */
public class AccessTokenAuthenticationFilter extends AuthenticatingFilter {

	/**
	 * shiro token 分组
	 */
	private String group = Constant.SHIROTOKEN_DEFAULT_GROUP;
	
	public AccessTokenAuthenticationFilter() {
		
	}
	
	/**
	 * 获取 token 分组
	 * @return
	 */
	public String getGroup() {
		return this.group;
	}
	
	/**
	 * 设置 token 分组
	 * @param group
	 */
	public void setGroup(String group) {
		this.group = group;
	}
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		// TODO Auto-generated method stub
		return super.isAccessAllowed(request, response, mappedValue);
	}
	
	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
		AccessToken token = new AccessToken();
		token.setGroup(this.getGroup());
		return token;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		return this.executeLogin(request, response);
	}

}
