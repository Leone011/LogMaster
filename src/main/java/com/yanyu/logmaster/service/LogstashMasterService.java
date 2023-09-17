package com.yanyu.logmaster.service;



public interface LogstashMasterService {
    void start(String collectorId);
    void stop();
    String getStatus();

    String test();
}
