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



    @PostMapping("/start")
    public ResponseEntity<String> startLogstash(@RequestBody CollectorInfo collectorInfo) {
        String pid = logstashService.start(collectorInfo);
        return ResponseEntity.ok("Collector start successfully");
    }

    @PostMapping("/stop")
    public ResponseEntity<String> stopLogstash(@RequestBody CollectorInfo collectorInfo) {
        logstashService.stop(collectorInfo);
        return ResponseEntity.ok("Logstash stopped successfully");
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateLogstash(@RequestBody CollectorInfo collectorInfo) {
        logstashService.update(collectorInfo);
        return ResponseEntity.ok("");
    }

    @PostMapping("/check")
    public ResponseEntity<String> checkLogstash(@RequestBody CollectorInfo collectorInfo) {
        String check = logstashService.check(collectorInfo);
        return ResponseEntity.ok(check);
    }

}