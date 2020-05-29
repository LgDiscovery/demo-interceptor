package com.logo.demo.inteceptor.component;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import java.util.Vector;

public interface ISFTPServer {

    ChannelSftp connect(String host,int port,String username,String password) throws JSchException;

    boolean upload(String directory,String uploadFile,ChannelSftp sftp) throws Exception;

    boolean download(String directory, String downloadFile, String saveFile, ChannelSftp sftp) throws Exception;

    Vector listFiles(String directory, ChannelSftp sftp) throws SftpException;

    boolean delete(String directory, String deleteFile, ChannelSftp sftp) throws SftpException;



}
