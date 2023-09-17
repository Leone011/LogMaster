package com.yanyu.logmaster.controller;

import com.yanyu.logmaster.service.LogstashMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogstashMonitorController {

    @Autowired
    private LogstashMonitorService logstashMonitorService;

    @GetMapping("/logstash/monitor")
    public String getLogstashStatus(@RequestParam(required = false, defaultValue = "/_node/stats") String apiPath) {
        return logstashMonitorService.getStatus(apiPath);
    }
}
