
package com.icat.quest.auth.filter;

import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.icat.quest.auth.service.SecurityServiceImpl;
import com.icat.quest.auth.utils.AuthAuthenticationToken;
import com.icat.quest.cache.service.RedisCacheService;
import com.icat.quest.cache.vo.ApplicationExceptionClassesVo;
import com.icat.quest.common.exception.AuthSecurityException;
import com.icat.quest.common.exception.SecurityBearerTokenException;
import com.icat.quest.common.exception.SecurityEmptyBearerTokenException;
import com.icat.quest.common.exception.SecurityInvalidFilterException;
import com.icat.quest.common.exception.SecurityTokenException;
import com.icat.quest.common.utils.Constants;
import com.icat.quest.common.vo.UserType;
import com.icat.quest.dao.CandidateDao;
import com.icat.quest.dao.TestConductorDao;
import com.icat.quest.model.Candidate;
import com.icat.quest.model.TestConductor;

public class AuthAuthenticatingFilter extends AuthenticatingFilter {

	@Autowired
	private TestConductorDao testConductorDao;

	@Autowired
	private CandidateDao userDao;

	@Autowired
	private SecurityServiceImpl securityServiceImpl;

	@Autowired
	private RedisCacheService redisCacheService;

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		Boolean canLogin = null;
		try {
			canLogin = executeLogin(request, response);
		} catch (Exception e) {

			httpServletResponse.setHeader("status", "error");
			httpServletResponse.setHeader("message", "Session Expired");
			httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

			return false;
		}

		if (canLogin) {
			String token = getToken(httpServletRequest);
			Map<String, Object> claims = securityServiceImpl.getClaimsByToken(token);
			String userName = claims.get(Constants.CLAIM_ATTR_SUB) != null
					? claims.get(Constants.CLAIM_ATTR_SUB).toString()
					: null;
		String type = claims.get(Constants.CLAIM_ATTR_TYPE) != null
				? claims.get(Constants.CLAIM_ATTR_TYPE).toString()
				: null;
			if (userName != null && type.equals(UserType.CANDIDATE.name())) {
				Candidate user = userDao.read(Integer.parseInt(userName));
				if (user == null) {
					return false;
				}
			} else {
				TestConductor testConductor = testConductorDao.read(Integer.parseInt(userName));
				if (testConductor == null) {
					return false;
				}
			}

		}

		return canLogin;
	}

	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String token = getToken(httpServletRequest);

		if (token == null || token.isEmpty()) {
			String exceptionClassName = SecurityTokenException.class.getName();
			String className = AuthAuthenticatingFilter.class.getName();
			ApplicationExceptionClassesVo applicationExceptionClassesVo = redisCacheService
					.getCachedRecordForApplicationExceptionClasses(exceptionClassName);
			String applicationMessage = applicationExceptionClassesVo != null
					? applicationExceptionClassesVo.getSystemExceptionMessageWithTag()
					: null;
			if (applicationMessage != null)
				applicationMessage = applicationMessage.replace("$token", token);
			throw new SecurityTokenException(applicationMessage, null, null, className);
		}
		return new AuthAuthenticationToken(token);
	}

	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
			ServletResponse response) {

		String exceptionClassName = SecurityException.class.getName();
		String className = AuthAuthenticatingFilter.class.getName();
		ApplicationExceptionClassesVo applicationExceptionClassesVo = redisCacheService
				.getCachedRecordForApplicationExceptionClasses(exceptionClassName);
		String applicationMessage = applicationExceptionClassesVo != null
				? applicationExceptionClassesVo.getSystemExceptionMessageNoTag()
				: null;
		String platformMessage = e.getMessage();
		throw new AuthSecurityException(applicationMessage, platformMessage, null, className);
	}

	@Override
	protected boolean isRememberMe(ServletRequest request) {

		return false;
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

		return false;
	}

	private String getToken(HttpServletRequest httpRequest) throws SecurityException {

		String token = null;
		final String authorizationHeader = httpRequest.getHeader("authorization");
		final String authQueryString = httpRequest.getQueryString() != null
				? httpRequest.getParameterMap().get("authorization")[0]
				: null;
		final String authorizationBearer;

		if (authorizationHeader == null && authQueryString == null) {
			String exceptionClassName = SecurityEmptyBearerTokenException.class.getName();
			String className = AuthAuthenticatingFilter.class.getName();
			ApplicationExceptionClassesVo applicationExceptionClassesVo = redisCacheService
					.getCachedRecordForApplicationExceptionClasses(exceptionClassName);
			String applicationMessage = applicationExceptionClassesVo != null
					? applicationExceptionClassesVo.getSystemExceptionMessageNoTag()
					: null;
			throw new SecurityEmptyBearerTokenException(applicationMessage, null, null, className);
		}

		if (authorizationHeader != null && authQueryString == null)
			authorizationBearer = authorizationHeader;
		else if (authorizationHeader == null && httpRequest.getRequestURI().contains(Constants.DISPATCHER_END_POINT))
			authorizationBearer = authQueryString;
		else {
			String exceptionClassName = SecurityInvalidFilterException.class.getName();
			String className = AuthAuthenticatingFilter.class.getName();
			ApplicationExceptionClassesVo applicationExceptionClassesVo = redisCacheService
					.getCachedRecordForApplicationExceptionClasses(exceptionClassName);
			String applicationMessage = applicationExceptionClassesVo != null
					? applicationExceptionClassesVo.getSystemExceptionMessageNoTag()
					: null;
			throw new SecurityInvalidFilterException(applicationMessage, null, null, className);
		}

		String[] parts = authorizationBearer.split(" ");
		if (parts.length != 2) {
			String exceptionClassName = SecurityBearerTokenException.class.getName();
			String className = AuthAuthenticatingFilter.class.getName();
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
