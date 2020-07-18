package com.spike.consumer;

import com.spike.TraceContext;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import java.io.IOException;
import java.util.UUID;

@Component
public class Consumer {

    private static Logger log = LoggerFactory.getLogger(Consumer.class);

    @JmsListener(destination = "audit")
    @LogExecutionTime("messageConsumer")
    public void receiveMessage(ActiveMQTextMessage message) throws JMSException, IOException {
        setTraceContext(message);
        log.info("Received message: {}", message.getText());

        if(message.getRedeliveryCounter() < 2) {
            log.error("Error condition");
            throw new RuntimeException("Some error occurred");
        }

        log.info("Message processed successfully");
    }

    private static void setTraceContext(ActiveMQTextMessage message) throws IOException {
        MDC.put(TraceContext.TRACE_ID, String.valueOf(message.getProperty(TraceContext.TRACE_ID_HEADER)));
        MDC.put(TraceContext.PARENT_SPAN_ID, String.valueOf(message.getProperty(TraceContext.PARENT_SPAN_ID_HEADER)));
        MDC.put(TraceContext.SPAN_ID, UUID.randomUUID().toString());
    }
}
