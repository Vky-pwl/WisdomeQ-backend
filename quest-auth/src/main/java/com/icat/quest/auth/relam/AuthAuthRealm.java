

package com.icat.quest.auth.relam;

import java.util.Map;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.icat.quest.auth.service.SecurityServiceImpl;
import com.icat.quest.auth.utils.AuthAuthenticationToken;
import com.icat.quest.cache.service.RedisCacheService;
import com.icat.quest.cache.vo.ApplicationExceptionClassesVo;
import com.icat.quest.common.exception.SecurityTokenException;
import com.icat.quest.common.exception.SecurityUserException;
import com.icat.quest.common.utils.Constants;




public class AuthAuthRealm extends AuthorizingRealm {

	private static final Logger		log							= LoggerFactory.getLogger(AuthAuthRealm.class);

	protected boolean				permissionsLookupEnabled	= true;

	@Autowired
	private SecurityServiceImpl			securityServiceImpl;

	@Autowired
	private RedisCacheService			redisCacheService;

	public AuthAuthRealm() {

		setAuthenticationTokenClass(AuthAuthenticationToken.class);
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		if (principals == null)
			throw new AuthorizationException("PrincipalCollection method argument cannot be null.");

		//String username = (String) getAvailablePrincipal(principals);

		Set<String> permissions = null;
/*		try {
			if (permissionsLookupEnabled) {
				permissions = getPermissions(username);
			}
		} catch (AuthorizationException e) {
			log.error("", e);
			throw new AuthorizationException(e);
		}
*/
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(permissions);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		AuthAuthenticationToken bearerToken = (AuthAuthenticationToken) token;

		String hashedToken = securityServiceImpl.sha256(bearerToken.getToken());

		Object value = redisCacheService.getValue(hashedToken);

		if (value != null) {
			String exceptionClassName = SecurityTokenException.class.getName();
			String className = AuthAuthRealm.class.getName();
			ApplicationExceptionClassesVo applicationExceptionClassesVo = redisCacheService.getCachedRecordForApplicationExceptionClasses(exceptionClassName);
			String applicationMessage = applicationExceptionClassesVo != null ? applicationExceptionClassesVo.getSystemExceptionMessageWithTag() : null;
			if (applicationMessage != null)
				applicationMessage = applicationMessage.replace("$token", hashedToken);
			throw new SecurityTokenException(applicationMessage, null, null, className);
		}

		Map<String, Object> claims = securityServiceImpl.getClaimsByToken(bearerToken.getToken());
		String userName = claims.get(Constants.CLAIM_ATTR_SUB) != null
				? claims.get(Constants.CLAIM_ATTR_SUB).toString()
				: null;
		String type = claims.get(Constants.CLAIM_ATTR_TYPE) != null
						? claims.get(Constants.CLAIM_ATTR_TYPE).toString()
						: null;
		String loginId = claims.get(Constants.CLAIM_ATTR_ULID) != null
								? claims.get(Constants.CLAIM_ATTR_ULID).toString()
								: null;
		String collegeId = claims.get(Constants.CLAIM_ATTR_CLID) != null
										? claims.get(Constants.CLAIM_ATTR_CLID).toString()
										: null;
		String principalName = userName+Constants.OPERATION_PRINCIPAL_TOKENIZER+type+Constants.OPERATION_PRINCIPAL_TOKENIZER+loginId;						
		if(collegeId != null) {
			principalName = principalName+Constants.OPERATION_PRINCIPAL_TOKENIZER+collegeId;
		}
		if (userName == null) {
			String exceptionClassName = SecurityUserException.class.getName();
			String className = AuthAuthRealm.class.getName();
			ApplicationExceptionClassesVo applicationExceptionClassesVo = redisCacheService.getCachedRecordForApplicationExceptionClasses(exceptionClassName);
			String applicationMessage = applicationExceptionClassesVo != null ? applicationExceptionClassesVo.getSystemExceptionMessageWithTag() : null;
			if (applicationMessage != null)
				applicationMessage = applicationMessage.replace("$userId", userName);
			throw new SecurityUserException(applicationMessage, null, null, className);
		}

		SimpleAuthenticationInfo info = null;
		try {
			info = new SimpleAuthenticationInfo(principalName, Constants.BEARER_AUTH_PASSWORD, getName());
		}
		catch (AuthorizationException e) {
			log.error("", e);
		throw new AuthenticationException(e);
		}

		return info;
	}
	
	public void setPermissionsLookupEnabled(boolean permissionsLookupEnabled) {

		this.permissionsLookupEnabled = permissionsLookupEnabled;
	}

}
