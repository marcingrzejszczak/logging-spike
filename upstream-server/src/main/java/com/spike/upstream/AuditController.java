package com.spike.upstream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuditController {

    private static Logger log = LoggerFactory.getLogger(AuditController.class);

    @PostMapping("/audit")
    public AuditResponse audit() {
        log.info("Received audit request");
        return new AuditResponse(true);
    }
}
