/*package com.icat.quest.app.config;

import org.slf4j.impl.Log4jLoggerFactory;
import org.springframework.web.servlet.DispatcherServlet;

public class LoggableDispatcherServlet extends DispatcherServlet {

    private final Log4jLoggerFactory logger = LogFactory.getLog(getClass());

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request);
        }
        if (!(response instanceof ContentCachingResponseWrapper)) {
            response = new ContentCachingResponseWrapper(response);
        }
        HandlerExecutionChain handler = getHandler(request);

        try {
            super.doDispatch(request, response);
        } finally {
            log(request, response, handler);
            updateResponse(response);
        }
    }

    private void log(HttpServletRequest requestToCache, HttpServletResponse responseToCache, HandlerExecutionChain handler) {
        LogMessage log = new LogMessage();
        log.setHttpStatus(responseToCache.getStatus());
        log.setHttpMethod(requestToCache.getMethod());
        log.setPath(requestToCache.getRequestURI());
        log.setClientIp(requestToCache.getRemoteAddr());
        log.setJavaMethod(handler.toString());
        log.setResponse(getResponsePayload(responseToCache));
        logger.info(log);
    }

    private String getResponsePayload(HttpServletResponse response) {
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (wrapper != null) {

            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                int length = Math.min(buf.length, 5120);
                try {
                    return new String(buf, 0, length, wrapper.getCharacterEncoding());
                }
                catch (UnsupportedEncodingException ex) {
                    // NOOP
                }
            }
        }
        return "[unknown]";
    }

    private void updateResponse(HttpServletResponse response) throws IOException {
        ContentCachingResponseWrapper responseWrapper =
            WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        responseWrapper.copyBodyToResponse();
    }

}*/