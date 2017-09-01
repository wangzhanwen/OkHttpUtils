package com.xingyun.okhttp.request;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.xingyun.okhttp.OkHttpUtils;
import com.xingyun.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RequestCall {
   private OkHttpRequest okHttpRequest;
   private Request request;
   private Call call;

   private long readTimeOut;
   private long writeTimeOut;
   private long connTimeOut;

   private OkHttpClient okHttpClient;

   public RequestCall(OkHttpRequest request){
       this.okHttpRequest = request;
   }

   public RequestCall readTimeOut(long readTimeOut)
   {
       this.readTimeOut = readTimeOut;
       return this;
   }

   public RequestCall writeTimeOut(long writeTimeOut){
       this.writeTimeOut = writeTimeOut;
       return this;
   }

   public RequestCall connTimeOut(long connTimeOut){
       this.connTimeOut = connTimeOut;
       return this;
   }

   public Call buildCall(Callback callback) {
      request = createRequest(callback);
      if (readTimeOut > 0 || writeTimeOut > 0 || connTimeOut >0) {
           readTimeOut = readTimeOut > 0 ? readTimeOut : OkHttpUtils.DEFAULT_MILLISECONDS;
           writeTimeOut = writeTimeOut > 0 ? writeTimeOut : OkHttpUtils.DEFAULT_MILLISECONDS;
           connTimeOut = connTimeOut > 0 ? connTimeOut : OkHttpUtils.DEFAULT_MILLISECONDS;

           okHttpClient = OkHttpUtils.getInstance().getOkHttpClient().newBuilder()
                   .readTimeout(readTimeOut, TimeUnit.MILLISECONDS)
                   .writeTimeout(writeTimeOut, TimeUnit.MILLISECONDS)
                   .connectTimeout(connTimeOut, TimeUnit.MILLISECONDS)
                   .build();
           call  = okHttpClient.newCall(request);
      }else {
          call = OkHttpUtils.getInstance().getOkHttpClient().newCall(request);
      }
      return call;
   }

   public Call getCall(){
       return call;
   }

   public Request getRequest(){
       return request;
   }

   public OkHttpRequest getOkHttpRequest(){
       return okHttpRequest;
   }

   private Request createRequest(Callback callback){
       return okHttpRequest.createRequest(callback);
   }

   public Response execute() throws IOException{
       buildCall(null);
       return call.execute();
   }

   public void execute(Callback callback){
       if (this == null) {
         System.out.println("this == null");
      }

       if (callback == null) {
               System.out.println("callback == null");
       }

       buildCall(callback);
       OkHttpUtils.getInstance().execute(this, callback);
   }


   public void cancel() {
      if (call != null) {
          call.cancel();
      }
   }


}
