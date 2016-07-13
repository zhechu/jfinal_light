package com.ugiant.modules.sys.security;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.ugiant.common.dict.Useable;
import com.ugiant.common.utils.Encodes;
import com.ugiant.modules.sys.model.Menu;
import com.ugiant.modules.sys.model.Role;
import com.ugiant.modules.sys.model.User;
import com.ugiant.modules.sys.service.SystemService;
import com.ugiant.modules.sys.utils.UserUtils;
/**
 * 系统安全认证实现类
 * @author lingyuwang
 *
 */
public class SystemAuthorizingRealm extends AuthorizingRealm{

	private SystemService systemService = SystemService.service;

    public SystemAuthorizingRealm() {
		super();
		// 设定密码校验的Hash算法与迭代次数
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(SystemService.HASH_ALGORITHM);
		matcher.setHashIterations(SystemService.HASH_INTERATIONS);
		setCredentialsMatcher(matcher);
	}

	/**
     * 认证回调函数,登录时调用.
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
    	AuthenticationInfo info = null;
    	UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		// 校验用户名密码
        User user = systemService.getUserByLoginName(token.getUsername());
		if (user != null) {
			if (Useable.NO.equals(user.getLoginFlag())){
				throw new AuthenticationException("msg:该已帐号禁止登录.");
			}
			byte[] salt = Encodes.decodeHex(user.getPassword().substring(0,16));
			info = new SimpleAuthenticationInfo(
					new Principal(user),
					user.getPassword().substring(16),
					ByteSource.Util.bytes(salt),
					getName());
			// 更新登录IP和时间
			systemService.updateUserLoginInfo(user);
		}
		return info;
    }
 
    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	Principal principal = (Principal) principals.fromRealm(getName()).iterator().next();
    	String loginName = principal.getLoginName();
    	User user = systemService.getUserByLoginName(loginName);
		if (user != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			List<Menu> list = UserUtils.getMenuList();
			for (Menu menu : list){
				String permission = menu.getPermission();
				if (StringUtils.isNotBlank(permission)){
					// 添加基于Permission的权限信息
					for (String p : StringUtils.split(permission, ",")){
						info.addStringPermission(p);
					}
				}
			}
			// 添加用户角色信息
			List<Role> roleList = (List<Role>) user.getRoleList();
			for (Role role : roleList){
				info.addRole(role.getEnname());
			}
			return info;
		} else {
			return null;
		}
    }
 
    /**
     * 更新用户授权信息缓存.
     */
    public void clearCachedAuthorizationInfo(String principal) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
        clearCachedAuthorizationInfo(principals);
    }
 
    /**
     * 清除所有用户授权信息缓存.
     */
    public void clearAllCachedAuthorizationInfo() {
    	Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null) {
            for (Object key : cache.keys()) {
                cache.remove(key);
            }
        }
    }

	/**
	 * 设定密码校验的Hash算法与迭代次数
	 */
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(SystemService.HASH_ALGORITHM);
		matcher.setHashIterations(SystemService.HASH_INTERATIONS);
		setCredentialsMatcher(matcher);
	}

	/**
	 * 授权用户信息
	 */
	public static class Principal implements Serializable {

		private static final long serialVersionUID = 6758028047686680870L;
		
		private Long id; // 编号
		private String loginName; // 登录名
		private String name; // 姓名
		
		public Principal(User user) {
			this.id = user.getId();
			this.loginName = user.getLoginName();
			this.name = user.getName();
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getLoginName() {
			return loginName;
		}

		public void setLoginName(String loginName) {
			this.loginName = loginName;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		/**
		 * 获取SESSIONID
		 */
		public String getSessionid() {
			try{
				return (String) UserUtils.getSession().getId();
			}catch (Exception e) {
				return "";
			}
		}
		
		public String toString() {
			return id.toString();
		}

	}
    
}
