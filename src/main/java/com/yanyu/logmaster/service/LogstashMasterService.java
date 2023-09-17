package com.yanyu.logmaster.service;


import com.yanyu.logmaster.entity.CollectorInfo;

public interface LogstashMasterService {
    void start(String collectorId);
    void stop();
    String getStatus();


    void create(CollectorInfo collectorInfo);
}
