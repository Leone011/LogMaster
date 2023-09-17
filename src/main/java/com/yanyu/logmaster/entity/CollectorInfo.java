package com.yanyu.logmaster.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CollectorInfo {
    private String collectorId;
    private String collectorName;
    private String collectorHost;
    private String installPath;
}
