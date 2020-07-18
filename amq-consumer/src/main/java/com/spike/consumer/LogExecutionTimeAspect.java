package com.spike.consumer;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static net.logstash.logback.argument.StructuredArguments.value;
import static net.logstash.logback.marker.Markers.append;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Aspect
@Component
public class LogExecutionTimeAspect {

    private static final String FULLY_QUALIFIED_METHOD_NAME_FORMAT = "%s.%s";
    public static Logger logger = LoggerFactory.getLogger(LogExecutionTimeAspect.class);

    @Around(value = "@annotation(annotation)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint, LogExecutionTime annotation) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            long executionTime = System.currentTimeMillis() - start;
            logger.info(
                    append("type", "performance"),
                    "{} executed in {} ms",
                    value("method", getMethodName(joinPoint, annotation)),
                    value("time", executionTime)
            );
        }
    }

    private String getMethodName(ProceedingJoinPoint joinPoint, LogExecutionTime annotation) {
        return isNotEmpty(annotation.value())
                ? annotation.value()
                : getFullyQualifiedMethodName(joinPoint);
    }

    private String getFullyQualifiedMethodName(ProceedingJoinPoint joinPoint) {
        return String.format(FULLY_QUALIFIED_METHOD_NAME_FORMAT,
                joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
    }

}
