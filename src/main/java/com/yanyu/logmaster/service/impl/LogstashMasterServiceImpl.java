package com.yanyu.logmaster.service.impl;

import com.yanyu.logmaster.entity.CollectorInfo;
import com.yanyu.logmaster.service.LogstashMasterService;
import com.yanyu.logmaster.utils.SshUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;



@Service
public class LogstashMasterServiceImpl implements LogstashMasterService {

    @Value("${logstashPackagePath}")
    private String logstashPackagePath;

    @Value("${logstashVersion}")
    private String logstashVersion;

    @Autowired
    private SshUtils sshUtils;


    @Override
    public String start(CollectorInfo collectorInfo) {
        // 获取logstash安装路径
        String logstashInstallPath = collectorInfo.getInstallPath();
        // 获取logstash配置文件目录
        String logstashConfigDir = collectorInfo.getConfDir();

        /* 获取logstash配置文件名
         * 1. 解析请求参数是否存在配置文件名称
         * - 存在
         *  confName = collectorInfo.getInfo
         * - 不存在
         *  默认值：agent_<collectorInfo.getCollectorId>.conf
         *
         */
        String confName;
        if (collectorInfo.getConfName() != null && !collectorInfo.getConfName().isEmpty()) {
            confName = collectorInfo.getConfName();
        } else {
            confName = "agent_" + collectorInfo.getCollectorId() + ".conf";
        }
        String configFileFullPath = logstashConfigDir + "/" + confName;
        // 构建start执行命令
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String logFilename = logstashInstallPath + "/job_log/agent_" + collectorInfo.getCollectorId() + "_" + timestamp + ".log";
        String pidFile = logstashInstallPath + "/log/agent_" + collectorInfo.getCollectorId() + "_pid.txt";
        String startCommand = String.format("nohup %s/bin/logstash -f %s > %s 2>&1 & echo $! > %s",
                logstashInstallPath, configFileFullPath, logFilename, pidFile);

        // Execute the command
        sshUtils.executeCommand(collectorInfo.getCollectorHost(), startCommand);

        // "ps aux | grep '[l]ogstash -f %s' | awk '{print $2}'"
        // Read the pid from the pid file
        String pid = sshUtils.executeCommand(collectorInfo.getCollectorHost(),"cat " + pidFile);
        return pid.trim(); // Return the PID
    }

    @Override
    public void stop(CollectorInfo collectorInfo) {
        // 获取logstash安装路径
        String logstashInstallPath = collectorInfo.getInstallPath();
        // 获取logstash配置文件目录
        String logstashConfigDir = collectorInfo.getConfDir();
    }


    @Override
    public void update(CollectorInfo collectorInfo) {

    }

    @Override
    public String check(CollectorInfo collectorInfo) {
        // Write the config content to a temporary file
        String logstashInstallPath = collectorInfo.getInstallPath();
        String tempConfigFilePath = logstashInstallPath + "/tmp/temp_logstash_config.conf";


        // Step 1: Append the config content to the remote file
        String escapedConfigContent = collectorInfo.getConfContent().replace("$", "\\$").replace("\"", "\\\"");  // Escape some special characters
        String appendToFileCommand = String.format("echo \"%s\" > %s", escapedConfigContent, tempConfigFilePath);
        sshUtils.executeCommand(collectorInfo.getCollectorHost(), appendToFileCommand);

        // Step 2: Use Logstash on the remote node to validate the configuration
        String validationCommand = String.format("%s/bin/logstash --config.test_and_exit -f %s", logstashInstallPath, tempConfigFilePath);

        // Execute the validation command on the remote node
        String output = sshUtils.executeCommand(collectorInfo.getCollectorHost(), validationCommand);

        // Step 3: Optionally, delete the temp file on the remote node
        String deleteCommand = String.format("rm %s", tempConfigFilePath);
        sshUtils.executeCommand(collectorInfo.getCollectorHost(), deleteCommand);

        if (output.contains("Configuration OK")) {
            return "Configuration is valid.";
        } else {
            // Extract error details from output or just return a generic error message
            return "Configuration validation failed. Details: " + output;
        }
    }
}
