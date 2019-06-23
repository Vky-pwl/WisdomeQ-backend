

package com.icat.quest.auth.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.internal.org.apache.commons.codec.binary.Base64;
import com.icat.quest.cache.service.RedisCacheService;
import com.icat.quest.cache.vo.ApplicationExceptionClassesVo;
import com.icat.quest.common.exception.AuthSecurityException;
import com.icat.quest.common.exception.SecurityClaimException;
import com.icat.quest.common.utils.SystemConfigResolver;


	
public class SecurityServiceImpl {

	private static final Logger	log	= LoggerFactory.getLogger(SecurityServiceImpl.class);

	@Autowired
	private RedisCacheService		redisCacheService;

	public Map<String, Object> getClaimsByToken(String token) {

		String secret = "";
		try {
			secret = Base64.encodeBase64String(SystemConfigResolver.getProperty("jwt.secret.key", "").getBytes("UTF-8"));
		}
		catch (UnsupportedEncodingException e) {
			log.error("", e);
			String exceptionClassName = AuthSecurityException.class.getName();
			String className = SecurityServiceImpl.class.getName();
			ApplicationExceptionClassesVo applicationExceptionClassesVo = redisCacheService.getCachedRecordForApplicationExceptionClasses(exceptionClassName);
			String applicationMessage = applicationExceptionClassesVo != null ? applicationExceptionClassesVo.getSystemExceptionMessageNoTag() : null;
			String platformMessage = e.getMessage();
			throw new AuthSecurityException(applicationMessage, platformMessage, null, className);
		}

		JWTVerifier jwtVerifier = new JWTVerifier(secret);
		Map<String, Object> claims = null;
		try {
			claims = jwtVerifier.verify(token);
		}
		catch (Exception e) {
			log.error("", e);
			String exceptionClassName = AuthSecurityException.class.getName();
			String className = SecurityServiceImpl.class.getName();
			ApplicationExceptionClassesVo applicationExceptionClassesVo = redisCacheService.getCachedRecordForApplicationExceptionClasses(exceptionClassName);
			String applicationMessage = applicationExceptionClassesVo != null ? applicationExceptionClassesVo.getSystemExceptionMessageNoTag() : null;
			String platformMessage = e.getMessage();
			throw new AuthSecurityException(applicationMessage, platformMessage, null, className);
		}

		if (claims == null) {
			String exceptionClassName = SecurityClaimException.class.getName();
			String className = SecurityServiceImpl.class.getName();
			ApplicationExceptionClassesVo applicationExceptionClassesVo = redisCacheService.getCachedRecordForApplicationExceptionClasses(exceptionClassName);
			String applicationMessage = applicationExceptionClassesVo != null ? applicationExceptionClassesVo.getSystemExceptionMessageNoTag() : null;
			throw new SecurityClaimException(applicationMessage, null, null, className);
		}

		return claims;
	}


	
	public String sha256(String base) {

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(base.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();

			for (byte element : hash) {
				String hex = Integer.toHexString(0xff & element);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}

			return hexString.toString();
		}
		catch (Exception ex) {
			String exceptionClassName = AuthSecurityException.class.getName();
			String className = SecurityServiceImpl.class.getName();
			ApplicationExceptionClassesVo applicationExceptionClassesVo = redisCacheService.getCachedRecordForApplicationExceptionClasses(exceptionClassName);
			String applicationMessage = applicationExceptionClassesVo != null ? applicationExceptionClassesVo.getSystemExceptionMessageNoTag() : null;
			String platformMessage = ex.getMessage();
			throw new AuthSecurityException(applicationMessage, platformMessage, null, className);
		}
	}

}
