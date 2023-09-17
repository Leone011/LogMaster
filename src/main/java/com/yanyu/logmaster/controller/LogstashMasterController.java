package com.yanyu.logmaster.controller;

import com.yanyu.logmaster.entity.CollectorInfo;
import com.yanyu.logmaster.service.LogstashMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/logstash")
public class LogstashMasterController {

    @Autowired
    private LogstashMasterService logstashService;


    @PostMapping("/create")
    public ResponseEntity<String> createCollector(@RequestBody CollectorInfo collectorInfo) {
        logstashService.create(collectorInfo);
        return ResponseEntity.ok("Collector created successfully for collector with ID: " + collectorInfo.getCollectorId());
    }

    @PostMapping("/start")
    public ResponseEntity<String> startLogstash(@RequestParam String collectorId) {
        logstashService.start(collectorId);
        return ResponseEntity.ok("Collector start successfully for collector with ID: " + collectorId);
    }

    @PostMapping("/stop")
    public ResponseEntity<String> stopLogstash() {
        logstashService.stop();
        return ResponseEntity.ok("Logstash stopped successfully");
    }

}