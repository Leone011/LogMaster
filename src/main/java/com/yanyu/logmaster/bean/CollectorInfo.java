package com.yanyu.logmaster.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CollectorInfo {
    private String collectorId;
    private String collectorName;
    private String collectorIp;
    private Integer collectorPort;
    private String collectorConfPath;
    private LocalDateTime createdAt;
}
