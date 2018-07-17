package com.lzy.libhttp.interceptor;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * 网络请求日志打印拦截器
 *
 * @author: cyli8
 * @date: 2018/7/16 10:21
 */
public class LogInterceptor implements Interceptor {
    private static final String TAG = "LogInterceptor";
    private boolean mShowResponse;

    public LogInterceptor(boolean showResponse) {
        this.mShowResponse = showResponse;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        logForRequest(request);
        Response response = chain.proceed(request);
        logForResponse(response);
        return response;
    }

    private void logForRequest(Request request) {
        try {
            String url = request.url().toString();
            Headers headers = request.headers();

            Logger.t(TAG).e("========== request'log start ==========");
            Logger.t(TAG).e("method : " + request.method());
            Logger.t(TAG).e("url : " + url);
            if (headers != null && headers.size() > 0) {
                Logger.t(TAG).e("headers : " + headers.toString());
            }
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    Logger.t(TAG).e("requestBody's contentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        Logger.t(TAG).e("requestBody's content : " + bodyToString(request));
                    } else {
                        Logger.t(TAG).e("requestBody's content : " + " maybe [file part] , too large to print , ignored!");
                    }
                }
            }
            Logger.t(TAG).e("========== request'log end ==========");
        } catch (Exception e) {
        }
    }

    private boolean isText(MediaType mediaType) {
        return mediaType.type() != null && mediaType.type().equals("text") ||
                mediaType.subtype() != null && (mediaType.subtype().equals("json") || mediaType.subtype().equals("xml") || mediaType.subtype().equals("html") || mediaType.subtype().equals("webviewhtml"));
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            RequestBody body = copy.body();
            if (body != null) {
                body.writeTo(buffer);
            }
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "show requestBody error :" + e.getMessage();
        }
    }

    private void logForResponse(Response response) {
        try {
            Logger.t(TAG).e("========== response'log start =========");
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            Logger.t(TAG).e("url : " + clone.request().url());
            Logger.t(TAG).e("code : " + clone.code());
            Logger.t(TAG).e("protocol : " + clone.protocol());
            if (!TextUtils.isEmpty(clone.message()))
                Logger.t(TAG).e("message : " + clone.message());
            if (mShowResponse) {
                ResponseBody body = clone.body();
                if (body != null) {
                    MediaType mediaType = body.contentType();
                    if (mediaType != null) {
                        Logger.t(TAG).e("responseBody's contentType : " + mediaType.toString());
                        if (isText(mediaType)) {
                            String resp = body.string();
                            Logger.t(TAG).e("responseBody's content : " + resp);
                            body = ResponseBody.create(mediaType, resp);
                            response.newBuilder().body(body).build();
                        } else {
                            Logger.t(TAG).e("responseBody's content : " + " maybe [file part] , too large to print , ignored!");
                        }
                    }
                }
            }
            Logger.t(TAG).e("========== response'log end ==========");
        } catch (Exception e) {
        }
    }

}
