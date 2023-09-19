package com.yanyu.logmaster.service;


import com.yanyu.logmaster.entity.CollectorInfo;

public interface LogstashMasterService {
    String start(CollectorInfo collectorInfo);
    void stop(CollectorInfo collectorInfo);
    void update(CollectorInfo collectorInfo);
    String check(CollectorInfo collectorInfo);
}
