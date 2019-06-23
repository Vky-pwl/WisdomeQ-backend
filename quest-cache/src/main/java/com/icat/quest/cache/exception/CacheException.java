
package com.icat.quest.cache.exception;

import com.icat.quest.cache.utils.RedisCacheConstants;


public class CacheException extends RuntimeException {

	private static final long	serialVersionUID	= 9091522463557394692L;
	public static final String				EXCEPTION_MESSAGE_SEPERATOR								= "~";
	public static final String				EXCEPTION_LEVEL_DEBUG									= "debug";


	public CacheException(String applicationMessage, String platformMessage, String userMessage, String className) {

		super(applicationMessage + RedisCacheConstants.CACHE_EXCEPTION_MESSAGE_SEPERATOR + platformMessage + RedisCacheConstants.CACHE_EXCEPTION_MESSAGE_SEPERATOR + userMessage
				+ RedisCacheConstants.CACHE_EXCEPTION_MESSAGE_SEPERATOR + className);
	}



}
