package com.spike;

import org.slf4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class TraceContextInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        MDC.put(TraceContext.TRACE_ID, request.getHeader(TraceContext.TRACE_ID_HEADER));
        MDC.put(TraceContext.PARENT_SPAN_ID, request.getHeader(TraceContext.PARENT_SPAN_ID_HEADER));
        MDC.put(TraceContext.SPAN_ID, UUID.randomUUID().toString());
        return true;
    }
}

