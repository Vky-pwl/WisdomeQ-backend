
package com.icat.quest.auth.filter;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.icat.quest.auth.service.SecurityServiceImpl;
import com.icat.quest.cache.service.RedisCacheService;
import com.icat.quest.cache.vo.ApplicationExceptionClassesVo;
import com.icat.quest.common.exception.SecurityBearerTokenException;
import com.icat.quest.common.exception.SecurityEmptyBearerTokenException;
import com.icat.quest.common.exception.SecurityTokenException;
import com.icat.quest.common.utils.Constants;
import com.icat.quest.common.vo.UserType;
import com.icat.quest.dao.UserLoginDao;
import com.icat.quest.model.UserLogin;

public class CustomLogoutFilter extends LogoutFilter {

	@Autowired
	private SecurityServiceImpl securityServiceImpl;

	@Autowired
	private RedisCacheService redisCacheService;

	@Autowired
	private UserLoginDao userLoginDao;

	private static final Logger log = LoggerFactory.getLogger(CustomLogoutFilter.class);

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String token = getToken(httpServletRequest);

		if (token == null || token.isEmpty()) {
			String exceptionClassName = SecurityTokenException.class.getName();
			String className = CustomLogoutFilter.class.getName();
			ApplicationExceptionClassesVo applicationExceptionClassesVo = redisCacheService
					.getCachedRecordForApplicationExceptionClasses(exceptionClassName);
			String applicationMessage = applicationExceptionClassesVo != null
					? applicationExceptionClassesVo.getSystemExceptionMessageWithTag()
					: null;
			if (applicationMessage != null)
				applicationMessage = applicationMessage.replace("$token", token);
			throw new SecurityTokenException(applicationMessage, null, null, className);
		}

		Map<String, Object> claims = securityServiceImpl.getClaimsByToken(token);

		String userName = claims.get(Constants.CLAIM_ATTR_SUB).toString();
		String userType = claims.get(Constants.CLAIM_ATTR_TYPE).toString();
		Subject subject = getSubject(request, response);

		try {
			subject.logout();
			logUserLogin(userName, userType);
		} catch (SessionException ise) {
			log.error("Encountered session exception during logout.  This can generally safely be ignored.", ise);
		}

		return false;
	}

	private void logUserLogin(String userName, String userType) {

		Map<String, Object> findByUserNameParameter = new HashMap<String, Object>();
		findByUserNameParameter.put("_1_userId", Integer.parseInt(userName));
		findByUserNameParameter.put("_2_userType", UserType.valueOf(userType));

		List<UserLogin> userLogins = userLoginDao.listEntityByParameter(UserLoginDao.findAllLogOffTimeNullByUserName,
				findByUserNameParameter, null, null);

		if (userLogins.size() > 0) {
			userLogins.forEach(userLogin -> {
				userLogin.setLogoffTime(new Date());
				userLogin.setLastModified(new Date());
				userLogin.setLastModifiedBy(Long.parseLong(userName));
				userLoginDao.update(userLogin);
			});

		}
	}

	private String getToken(HttpServletRequest httpRequest) throws SecurityException {

		String token = null;
		final String authorizationHeader = httpRequest.getHeader("authorization");
		if (authorizationHeader == null) {
			String exceptionClassName = SecurityEmptyBearerTokenException.class.getName();
			String className = CustomLogoutFilter.class.getName();
			ApplicationExceptionClassesVo applicationExceptionClassesVo = redisCacheService
					.getCachedRecordForApplicationExceptionClasses(exceptionClassName);
			String applicationMessage = applicationExceptionClassesVo != null
					? applicationExceptionClassesVo.getSystemExceptionMessageNoTag()
					: null;
			throw new SecurityEmptyBearerTokenException(applicationMessage, null, null, className);
		}

		String[] parts = authorizationHeader.split(" ");
		if (parts.length != 2) {
			String exceptionClassName = SecurityBearerTokenException.class.getName();
			String className = CustomLogoutFilter.class.getName();
			ApplicationExceptionClassesVo applicationExceptionClassesVo = redisCacheService
					.getCachedRecordForApplicationExceptionClasses(exceptionClassName);
			String applicationMessage = applicationExceptionClassesVo != null
					? applicationExceptionClassesVo.getSystemExceptionMessageNoTag()
					: null;
			throw new SecurityBearerTokenException(applicationMessage, null, null, className);
		}

		String scheme = parts[0];
		String credentials = parts[1];

		Pattern pattern = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE);
		if (pattern.matcher(scheme).matches())
			token = credentials;
		return token;
	}
}
