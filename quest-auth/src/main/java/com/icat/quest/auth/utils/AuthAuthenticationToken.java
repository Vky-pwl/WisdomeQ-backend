
package com.icat.quest.auth.utils;

import java.util.Map;

import org.apache.shiro.authc.AuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;

import com.icat.quest.auth.service.SecurityServiceImpl;
import com.icat.quest.common.utils.Constants;


public class AuthAuthenticationToken implements AuthenticationToken {

	private static final long	serialVersionUID	= 6368484173522945221L;
	private String				token;

	@Autowired
	private SecurityServiceImpl		securityServiceImpl;

	public AuthAuthenticationToken(String token) {

		this.token = token;
	}

	@Override
	public Object getPrincipal() {

		Map<String, Object> claims = securityServiceImpl.getClaimsByToken(token);
		String userName = claims.get(Constants.CLAIM_ATTR_SUB) != null ? claims.get(Constants.CLAIM_ATTR_SUB).toString(): null;
		return userName;
	}

	@Override
	public Object getCredentials() {

		return Constants.BEARER_AUTH_PASSWORD;
	}

	public String getToken() {

		return token;
	}
}
