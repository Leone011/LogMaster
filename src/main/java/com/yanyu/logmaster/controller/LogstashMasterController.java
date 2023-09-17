package com.yanyu.logmaster.controller;

import com.yanyu.logmaster.service.LogstashMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logstash")
public class LogstashMasterController {

    @Autowired
    private LogstashMasterService logstashService;

    @PostMapping("/start")
    public ResponseEntity<String> startLogstash(@RequestParam String collectorId) {
        logstashService.start(collectorId);
        return ResponseEntity.ok("Logstash create successfully for collector with ID: " + collectorId);
    }

    @PostMapping("/stop")
    public ResponseEntity<String> stopLogstash() {
        logstashService.stop();
        return ResponseEntity.ok("Logstash stopped successfully");
    }

}