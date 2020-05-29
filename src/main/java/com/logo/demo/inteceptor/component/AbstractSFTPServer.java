package com.logo.demo.inteceptor.component;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.Vector;

public  abstract class AbstractSFTPServer implements ISFTPServer{
    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(AbstractSFTPServer.class.getName());
    public Session session;
    public Channel channel;
    public ChannelSftp sftp = null;

    /**
     * Description: 连接sftp服务器
     * @param host 主机
     * @param port 端口
     * @param username 用户名
     * @param password 密码
     * @return sftp
     */
    @Override
    public ChannelSftp connect(String host, int port, String username,
                               String password) throws JSchException {

        JSch jsch = new JSch();
        session = jsch.getSession(username, host, port);
        logger.info("Session created...");
        session.setPassword(password);
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        session.setConfig(sshConfig);
        session.connect();
        logger.info("Session connected...");
        channel = session.openChannel("sftp");
        logger.info("Opening Channel...");
        channel.connect();
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        sftp = (ChannelSftp) channel;
        logger.info("Connected to " + host + " is sucess!");
        return sftp;
    }

    /**
     * Description: 获取SFTP服务器上指定目录下的文件
     * @param directory 指定的目录
     * @param sftp
     * @return Vector
     */
    @Override
    public Vector listFiles(String directory, ChannelSftp sftp)
            throws SftpException {
        try {
            logger.info("get " + directory + " files");
            return sftp.ls(directory);
        } finally {
            close();
        }
    }

    /**
     * Description: 关闭系统资源
     */
    public void close () {
        if (sftp.isConnected())
            sftp.disconnect();
        logger.info("ChannelSftp close!");
        if (channel.isConnected())
            channel.disconnect();
        logger.info("Channel close!");
        if (session.isConnected())
            session.disconnect();
        logger.info("Session close!");
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
