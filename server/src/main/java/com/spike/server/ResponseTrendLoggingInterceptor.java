package com.spike.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

import static net.logstash.logback.argument.StructuredArguments.value;
import static net.logstash.logback.marker.Markers.append;

public class ResponseTrendLoggingInterceptor implements ClientHttpRequestInterceptor {

    private static Logger logger = LoggerFactory.getLogger(ResponseTrendLoggingInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request,
                                        byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        ClientHttpResponse response = null;
        long start = System.currentTimeMillis();
        try {
            response = execution.execute(request, body);
        } finally {
            long executionTime = System.currentTimeMillis() - start;
            if (response != null) {
                logger.info(
                        append("type", "upstream_response"),
                        "API call to {} responded in {} ms with status {}",
                        value("path", request.getURI().getPath()),
                        value("time", executionTime),
                        value("statusCode", response.getRawStatusCode())
                );
            }
        }
        return response;
    }
}
