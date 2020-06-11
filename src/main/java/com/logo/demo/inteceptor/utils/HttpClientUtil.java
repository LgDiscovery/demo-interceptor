package com.logo.demo.inteceptor.utils;

import com.logo.demo.inteceptor.constant.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;

/**
 *  手动封装httpClient 请求客户端
 */
public class HttpClientUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);

    private String strCharSet;
    private String strContentType;
    private String strAccept;
    private String strAcceptEncoding;
    private String strUserAgent;
    private String strPragma;

    public String getStrCharSet() {
        return strCharSet;
    }

    public HttpClientUtil(){
        strCharSet = System.getProperty("JAVA_VB.encode","UTF-8");
        strContentType = "application/x-www-form-urlencoded";
        strAccept = "text/xml; *.*";
        strAcceptEncoding = "gzip, deflate";
        strUserAgent = "HTTP Transparent";
        strPragma = "no-cache";
    }


    /**
     *
     * @param requestUrl 请求url
     * @param requestMethod 请求方法
     * @param outputStr 请求参数 是 name1=value1&name2=value2 的形式
     * @return
     */
    public String httpRequest(String requestUrl,String requestMethod,String outputStr){
        StringBuffer buffer = new StringBuffer();
        HttpURLConnection connection = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedReader br = null;
        try {
            URL url = new URL(requestUrl);
            LOGGER.debug("建立连接--------{}", requestUrl);
            //通过远程url连接对象打开连接
            connection = (HttpURLConnection) url.openConnection();
            //设置请求方式
            connection.setRequestMethod(requestMethod);
            //设置连接主机服务器超时时间
            connection.setConnectTimeout(Const.CONNECTTIMEOUT);
            //设置读取主机服务器返回数据超时时间
            connection.setReadTimeout(Const.SOTIMEOUT);
            // 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true
            connection.setDoOutput(true);
            // 默认值为：true，当前向远程服务读取数据时，设置为true，该参数可有可无
            connection.setDoInput(true);
            // 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
            connection.setRequestProperty("Content-Type", strContentType);
            connection.setRequestProperty("Accept", strAccept);
            connection.setRequestProperty("Accept-Encoding", strAcceptEncoding);
            connection.setRequestProperty("User-Agent", strUserAgent);
            connection.setRequestProperty("Pragma", strPragma);

            long start = Instant.now().toEpochMilli();
            //通过输出流对象将参数写出去、传输出去，通过字节数组写出去
            LOGGER.debug("发送数据内容---------------{}", outputStr);
            //往主机服务器写内容
            if (null != outputStr) {
                os = connection.getOutputStream();
                os.write(outputStr.getBytes(getStrCharSet()));
            }

            //读取主机服务器返回数据
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                // 对输入流对象进行包装:charset根据工作项目组的要求来设置
                br = new BufferedReader(new InputStreamReader(is, getStrCharSet()));
                String temp = null;
                // 循环遍历一行一行读取数据
                while ((temp = br.readLine()) != null) {
                    buffer.append(temp);
                }
                long end = Instant.now().toEpochMilli();

                LOGGER.debug("耗时----------------------{}", (end - start));
                LOGGER.debug("返回得数据------------------{}", buffer);
            }
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            //关闭资源
            closeStream(is, os, br);

            //断开与远程地址url的连接
            connection.disconnect();
        }
        return buffer.toString();
    }

    private void closeStream(InputStream is, OutputStream os, BufferedReader br) {
        if(null != br){
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(null != os){
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(null != is){
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
