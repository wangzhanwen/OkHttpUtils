package com.xingyun.okhttp.builder;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.xingyun.okhttp.request.GetRequest;
import com.xingyun.okhttp.request.RequestCall;

public class GetBuilder extends OkHttpRequestBuilder<GetBuilder> implements HasParamsable{

    @Override
    public RequestCall build() {
        if (params != null) {
            url = appendParams(url, params);
        }
        return new GetRequest(url, tag, params, headers).build();
    }

    /**
     * url 拼接请求参数
     * @param url
     * @param params
     * @return
     */
    protected String  appendParams(String url, Map<String, String> params) {
       if (url == null || params == null || params.isEmpty()) {
          return url;
       }
       //拼接请求参数
       Iterator<String> it = params.keySet().iterator();
       StringBuffer urlBuffer = null;
       while (it.hasNext()) {
            String key = it.next();
            String value = params.get(key);
            if (urlBuffer == null) {
               urlBuffer = new StringBuffer();
               urlBuffer.append("?");
            } else {
                urlBuffer.append("&");
            }
            urlBuffer.append(key);
            urlBuffer.append("=");
            urlBuffer.append(value);
       }
       url += urlBuffer.toString();

      return url;
    }

    @Override
    public GetBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    @Override
    public GetBuilder addParams(String key, String val) {
        if (this.params == null){
            params = new LinkedHashMap<>();
        }
        params.put(key, val);
        return this;
    }
}
