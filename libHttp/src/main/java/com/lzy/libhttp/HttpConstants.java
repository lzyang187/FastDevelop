package com.lzy.libhttp;

/**
 * @author: cyli8
 * @date: 2018/7/16 10:15
 */
public class HttpConstants {
    public static final int CONNECT_TIMEOUT = 10;
    public static final int READ_TIMEOUT = 10;
    public static final int WRITE_TIMEOUT = 10;

    /*请求返回码正常*/
    public static final int HTTP_TYPE_OK = 1;
    /*请求超时*/
    public static final int HTTP_TYPE_TIMEOUT = -1;
    /*网络连接异常*/
    public static final int HTTP_TYPE_NETWORK_ERROR = -2;
    /*网络请求服务器错误*/
    public static final int HTTP_TYPE_SERVER_REFUSE = -4;
    /*网络请求其他异常*/
    public static final int HTTP_TYPE_OTHER_EXCEPTION = -8;

}
