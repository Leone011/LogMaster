@Override
public void create(String collectorInfo) {
        // 构建Logstash安装包的URL
        String logstashPackageUrl = AppConfig.REPOSITORY_BASE_URL + "logstash-" + LOGSTASH_VERSION + ".tar.gz";

        // 设置下载文件的保存路径
        String downloadPath = "/path/to/download/directory/logstash-" + LOGSTASH_VERSION + ".tar.gz"; // 请替换为实际的下载路径

        try {
        // 创建URL对象
        URL url = new URL(logstashPackageUrl);

        // 打开URL连接
        try (BufferedInputStream in = new BufferedInputStream(url.openStream());
        FileOutputStream fileOutputStream = new FileOutputStream(downloadPath)) {

        byte[] dataBuffer = new byte[1024];
        int bytesRead;

        // 从URL读取数据并保存到本地文件
        while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
        fileOutputStream.write(dataBuffer, 0, bytesRead);
        }

        System.out.println("Logstash安装包下载完成：" + downloadPath);

        // 获取Logstash安装包后，执行安装操作
        installLogstash(downloadPath);

        } catch (IOException e) {
        e.printStackTrace();
        }
        } catch (IOException e) {
        e.printStackTrace();
        }
        }
