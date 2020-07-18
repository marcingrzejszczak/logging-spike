package com.spike.upstream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import static com.spike.TraceContext.*;

@RestController
public class AuditController {
    private static Logger log = LoggerFactory.getLogger(AuditController.class);
    private JmsTemplate template;

    public AuditController(JmsTemplate template) {
        this.template = template;
    }

    @PostMapping("/audit")
    public AuditResponse audit() {
        log.info("Received audit request");
        template.send("audit", session -> {
            final TextMessage message = session.createTextMessage("hello world");
            message.setStringProperty(TRACE_ID_HEADER, MDC.get(TRACE_ID));
            message.setStringProperty(PARENT_SPAN_ID_HEADER, MDC.get(SPAN_ID));
            return message;
        });
        return new AuditResponse(true);
    }
}
