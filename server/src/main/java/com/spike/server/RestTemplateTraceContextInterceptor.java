package com.spike.server;

import org.slf4j.MDC;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

import static com.spike.TraceContext.*;

public class RestTemplateTraceContextInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request,
                                        byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        request.getHeaders().add(TRACE_ID_HEADER, MDC.get(TRACE_ID));
        request.getHeaders().add(PARENT_SPAN_ID_HEADER, MDC.get(SPAN_ID));
        return execution.execute(request, body);
    }
}
