
package com.icat.quest.common.utils;

import java.util.HashMap;
import java.util.Map;

public class ExceptionResponseBuilder {

	public static enum Status {
		success, error
	}

	private Map<String, Object>	responseMap;

	public ExceptionResponseBuilder() {

		responseMap = new HashMap<>();
		responseMap.put("errorResponse", new HashMap<String, Object>());
	}

	public ExceptionResponseBuilder status(Status status) {

		responseMap.put("status", status.toString());
		return this;
	}

	public ExceptionResponseBuilder platformMessage(String platformMessage) {

		@SuppressWarnings("unchecked")
		Map<String, Object> errorResponseMap = (Map<String, Object>) responseMap.get("errorResponse");
		errorResponseMap.put("platformMessage", platformMessage);
		return this;
	}

	public ExceptionResponseBuilder applicationMessage(String applicationMessage) {

		@SuppressWarnings("unchecked")
		Map<String, Object> errorResponseMap = (Map<String, Object>) responseMap.get("errorResponse");
		errorResponseMap.put("applicationMessage", applicationMessage);
		return this;
	}

	public ExceptionResponseBuilder stackTrace(Exception e, boolean canWrite) {

		if (canWrite) {
			StackTraceElement[] stackTraces = e.getStackTrace();
			StringBuffer exceptionStack = new StringBuffer();
			for (StackTraceElement stackTrace : stackTraces)
				exceptionStack.append(stackTrace.toString());
			@SuppressWarnings("unchecked")
			Map<String, Object> errorResponseMap = (Map<String, Object>) responseMap.get("errorResponse");
			errorResponseMap.put("stackTrace", exceptionStack.toString());
		}
		return this;
	}

	public ExceptionResponseBuilder className(String className) {

		@SuppressWarnings("unchecked")
		Map<String, Object> errorResponseMap = (Map<String, Object>) responseMap.get("errorResponse");
		errorResponseMap.put("className", className);
		return this;
	}

	public ExceptionResponseBuilder systemExceptionClassHandler(String systemExceptionClassHandler) {

		@SuppressWarnings("unchecked")
		Map<String, Object> errorResponseMap = (Map<String, Object>) responseMap.get("errorResponse");
		errorResponseMap.put("systemExceptionClassHandler", systemExceptionClassHandler);
		return this;
	}

	public ExceptionResponseBuilder userMessage(String userMessage) {

		@SuppressWarnings("unchecked")
		Map<String, Object> errorResponseMap = (Map<String, Object>) responseMap.get("errorResponse");
		errorResponseMap.put("userMessage", userMessage);
		return this;
	}

	public Map<String, Object> build() {

		return responseMap;
	}
}
