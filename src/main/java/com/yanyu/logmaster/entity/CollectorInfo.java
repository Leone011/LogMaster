package com.yanyu.logmaster.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectorInfo {
    private String collectorId;
    private String collectorName;
    private String collectorHost;
    private String confDir;
    private String confName;
    private String confContent;
    private String collectorPort;
    private String installPath;
}
