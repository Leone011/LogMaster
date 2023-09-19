package com.yanyu.logmaster.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StartResponse {
    private String collectorId;
    private boolean startSuccessfully;
    private String message;
}
