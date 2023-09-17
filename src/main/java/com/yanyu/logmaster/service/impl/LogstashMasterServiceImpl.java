package com.yanyu.logmaster.service.impl;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.yanyu.logmaster.entity.CollectorInfo;
import com.yanyu.logmaster.service.LogstashMasterService;
import com.yanyu.logmaster.utils.SshUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;


@Service
public class LogstashMasterServiceImpl implements LogstashMasterService {

    @Value("${logstashPackagePath}")
    private String logstashPackagePath;

    @Autowired
    private SshUtils sshUtils;



    /**
     * 1. local -> remote
     * @param collectorInfo
     * {
     *     "collectorId": "1",
     *     "collectorName": "test",
     *     "collectorIp": "hadoop101",
     *     "installPath": "/opt/module/logstash"
     * }
     * 默认ssh端口号为22
     * 本地和远端用户相同且免密
     */
    @Override
    public void create(CollectorInfo collectorInfo) {



        // 1. 发送安装包和认证文件到host上
        String collectorHost = collectorInfo.getCollectorHost();
        String collectorInfoInstallPath  = collectorInfo.getInstallPath();

        // 构建创建目标文件夹命令
        String createDirCommand = "mkdir -p " + collectorInfoInstallPath;

        // 构建 SCP 命令
        // 校验本地文件路径是否存在

        String scpCommand = "scp " + logstashPackagePath + " " + collectorInfoInstallPath + "/logstash.tar.gz" ;

        try {
            // 执行创建目标文件夹命令
            String createDirResult  = sshUtils.executeCommand(collectorHost, createDirCommand);

            // 处理创建目标文件夹的结果
            if (createDirResult.isEmpty()) {
                System.out.println("Target directory created successfully.");
            } else {
                System.out.println("Failed to create target directory. Result: " + createDirResult);
            }

            // 执行 SCP 命令
            String result = sshUtils.executeCommand(collectorHost, scpCommand);

            // 处理结果
            if (result.isEmpty()) {
                System.out.println("File copied successfully.");
            } else {
                System.out.println("File copy failed. Result: " + result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 处理异常
        }

        // 3. 解压安装包，配置采集器的监控端口

        // 4. 初始化 collector_id.conf 文件


        // 测试
        // 构建检查文件存在性的命令
        String checkCommand = "test -e " + collectorInfo.getInstallPath() + "/collector_+" + collectorInfo.getCollectorId() + ".conf";

        String result = sshUtils.executeCommand(collectorInfo.getCollectorHost(), checkCommand);

        // 检查结果
        if (result.isEmpty()) {
            System.out.println("collector_id.conf does not exist.");
        } else {
            System.out.println("collector_id.conf exists.");
        }
    }

    @Override
    public void start(String collectorId) {
        // 根据采集器id从pg数据库中配置文件路径

//        executeCommand(LOGSTASH_START_COMMAND);
    }

    @Override
    public void stop() {
//        executeCommand(LOGSTASH_STOP_COMMAND);
    }

    @Override
    public String getStatus() {
//        return executeCommand(LOGSTASH_STATUS_COMMAND);
        return "1";
    }

}
