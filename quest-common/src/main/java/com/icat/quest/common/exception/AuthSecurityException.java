

package com.icat.quest.common.exception;

import com.icat.quest.common.utils.Constants;

public class AuthSecurityException extends RuntimeException {

	private static final long	serialVersionUID	= 9091522463557394692L;

	public AuthSecurityException(String applicationMessage, String platformMessage, String userMessage, String className) {

		super(applicationMessage + Constants.EXCEPTION_MESSAGE_SEPERATOR + platformMessage + Constants.EXCEPTION_MESSAGE_SEPERATOR + userMessage
				+ Constants.EXCEPTION_MESSAGE_SEPERATOR + className);
	}

}
