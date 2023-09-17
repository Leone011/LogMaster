package com.yanyu.logmaster.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@RestController
@RequestMapping("/collector")
public class CollectorController {

    private static final String COLLECTOR_DIR = "/opt/module/logstash/job/";

    @GetMapping("/{collectorName}")
    public String getCollectorConfig(@PathVariable String collectorName) throws IOException {
        File file = new File(COLLECTOR_DIR + collectorName + ".conf");
        return new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
    }

    @PostMapping("/{collectorName}")
    public ResponseEntity<String> updateCollectorConfig(@PathVariable String collectorName, @RequestBody String content) throws IOException {
        File file = new File(COLLECTOR_DIR + collectorName + ".conf");
        Files.write(file.toPath(), content.getBytes(StandardCharsets.UTF_8));
        return ResponseEntity.ok("Configuration updated successfully.");
    }
}
