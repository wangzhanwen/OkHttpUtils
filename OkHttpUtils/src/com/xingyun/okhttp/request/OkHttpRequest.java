package com.xingyun.okhttp.request;

import java.util.Map;

import com.xingyun.okhttp.callback.Callback;
import com.xingyun.okhttp.utils.Exceptions;

import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;

public abstract class OkHttpRequest {
    protected String url;
    protected Object tag;
    protected Map<String, String> params;
    protected Map<String, String> headers;

    protected Request.Builder builder = new Request.Builder();

    protected OkHttpRequest(String url, Object tag,
            Map<String, String> params, Map<String, String> headers){
        this.url = url;
        this.tag = tag;
        this.params = params;
        this.headers = headers;

        if (url == null){
           Exceptions.illegalArgument("url 不可以为 null！");
        }

        initBuilder();
    }

    public RequestCall build(){
        return new RequestCall(this);
    }

    public Request createRequest(Callback callback){
        RequestBody requestBody = buildRequestBody();
        RequestBody wrappedRequestBody = wrapRequestBody(requestBody, callback);
        Request request = buildRequest(wrappedRequestBody);
        return request;
    }

    protected RequestBody wrapRequestBody(RequestBody requestBody, final Callback callback){
        return requestBody;
    }

    /**
     * 初始化一些基本参数 url , tag , headers
     */
    private void initBuilder(){
        builder.url(url).tag(tag);
        appendHeaders();
    }

    /**
     * 添加请求头
     */
    protected void appendHeaders() {
       Headers.Builder headerBuilder = new Headers.Builder();
       if (headers == null || headers.isEmpty()) {
           return;
       }
       for (String key  : headers.keySet()) {
         headerBuilder.add(key, headers.get(key));
       }
       builder.headers(headerBuilder.build());
    }

    protected abstract RequestBody buildRequestBody();
    protected abstract Request buildRequest(RequestBody requestBody);
}
