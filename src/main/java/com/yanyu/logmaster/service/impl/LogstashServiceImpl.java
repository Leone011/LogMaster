package com.yanyu.logmaster.service.impl;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.yanyu.logmaster.service.LogstashMasterService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
public class LogstashServiceImpl implements LogstashMasterService {

    private static final String REMOTE_HOST = "hadoop100";
    private static final int SSH_PORT = 22;
    private static final String SSH_USERNAME = "yanyu";
    private static final String SSH_PASSWORD = "1011";
    private static final String STRICT_HOST_KEY_CHECKING_CONFIG = "StrictHostKeyChecking";
    private static final String STRICT_HOST_KEY_CHECKING_VALUE = "no";

    private static final String LOGSTASH_START_COMMAND = "/opt/module/logstash/bin/logstash -f /opt/module/logstash/job/test1.conf &";
    private static final String LOGSTASH_STOP_COMMAND = "your_stop_command_here";
    private static final String LOGSTASH_STATUS_COMMAND = "your_status_command_here";

    @Override
    public void start(String collectorId) {
        // 根据采集器id从pg数据库中配置文件路径

        executeCommand(LOGSTASH_START_COMMAND);
    }

    @Override
    public void stop() {
        executeCommand(LOGSTASH_STOP_COMMAND);
    }

    @Override
    public String getStatus() {
        return executeCommand(LOGSTASH_STATUS_COMMAND);
    }

    @Override
    public String test() {
        System.out.println("test");
        Session session = null;
        ChannelExec channel = null;
        StringBuilder output = new StringBuilder();

        try {
            JSch jsch = new JSch();
            session = jsch.getSession(SSH_USERNAME, REMOTE_HOST, SSH_PORT);
            session.setPassword(SSH_PASSWORD);
            session.setConfig(STRICT_HOST_KEY_CHECKING_CONFIG, STRICT_HOST_KEY_CHECKING_VALUE);
            session.connect();

            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand("ls /opt/module");

            InputStream in = channel.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            channel.connect();

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

        } catch (Exception e) {
            throw new RuntimeException("Error executing test command", e);
        } finally {
            if (channel != null && channel.isConnected()) {
                channel.disconnect();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }

        return output.toString();
    }


    private String executeCommand(String command) {
        System.out.println("execute" + command);
        Session session = null;
        ChannelExec channel = null;
        StringBuilder output = new StringBuilder();

        try {
            JSch jsch = new JSch();
            session = jsch.getSession(SSH_USERNAME, REMOTE_HOST, SSH_PORT);
            session.setPassword(SSH_PASSWORD);
            session.setConfig(STRICT_HOST_KEY_CHECKING_CONFIG, STRICT_HOST_KEY_CHECKING_VALUE);
            session.connect();

            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);

            InputStream in = channel.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            channel.connect();

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

        } catch (Exception e) {
            throw new RuntimeException("Error executing test command", e);
        } finally {
            if (channel != null && channel.isConnected()) {
                channel.disconnect();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }

        return output.toString();
    }
}
