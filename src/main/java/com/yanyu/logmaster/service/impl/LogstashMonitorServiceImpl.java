package com.yanyu.logmaster.service.impl;

import com.yanyu.logmaster.service.LogstashMonitorService;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class LogstashMonitorServiceImpl implements LogstashMonitorService {

    private static final String LOGSTASH_IP = "hadoop100"; // 替换为你的Logstash IP
    private static final int LOGSTASH_MONITOR_PORT = 9600; // 默认的Logstash监控API端口是9600

    @Override
    public String getStatus(String apiPath) {
        String urlString = "http://" + LOGSTASH_IP + ":" + LOGSTASH_MONITOR_PORT + apiPath;

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                output.append(line);
            }

            conn.disconnect();
            return output.toString();

        } catch (Exception e) {
            throw new RuntimeException("Error fetching Logstash status from API path: " + apiPath, e);
        }
    }
}
