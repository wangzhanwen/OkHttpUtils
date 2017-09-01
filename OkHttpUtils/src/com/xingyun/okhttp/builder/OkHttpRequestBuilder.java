package com.xingyun.okhttp.builder;

import java.util.LinkedHashMap;
import java.util.Map;

import com.xingyun.okhttp.request.RequestCall;

public abstract class OkHttpRequestBuilder<T extends OkHttpRequestBuilder> {
      protected String url;
      protected Object tag;
      protected Map<String, String> headers;
      protected Map<String, String> params;

      public T url(String url) {
         this.url = url;
         return (T)this;
      }


      public T tag(Object tag) {
          this.tag = tag;
          return (T)this;
       }

      public T headers(Map<String, String> headers) {
           this.headers = headers;
          return (T)this;
     }

      public T addHeader(String key, String value){
          if (this.headers == null){
              headers = new LinkedHashMap<>();
          }
          headers.put(key, value);
          return (T) this;
      }

      public abstract RequestCall build();
}
