package com.xingyun.okhttp;

import java.io.IOException;

import com.xingyun.okhttp.builder.GetBuilder;
import com.xingyun.okhttp.builder.PostFileBuilder;
import com.xingyun.okhttp.builder.PostFormBuilder;
import com.xingyun.okhttp.builder.PostStringBuilder;
import com.xingyun.okhttp.callback.Callback;
import com.xingyun.okhttp.request.RequestCall;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;


public class OkHttpUtils {
     public static final long DEFAULT_MILLISECONDS = 10_000L;
     private static OkHttpUtils mInstance;
     private OkHttpClient mOkHttpClient = null;

     public OkHttpUtils(OkHttpClient okHttpClient){
         if (okHttpClient == null){
             mOkHttpClient = new OkHttpClient();
         } else{
             mOkHttpClient = okHttpClient;
         }
     }

     public static OkHttpUtils initClient(OkHttpClient okHttpClient){
         if (mInstance == null){
             synchronized (OkHttpUtils.class){
                 if (mInstance == null){
                     mInstance = new OkHttpUtils(okHttpClient);
                 }
             }
         }
         return mInstance;
     }

     public OkHttpClient getOkHttpClient(){
         return mOkHttpClient;
     }

     public static OkHttpUtils getInstance(){
         return initClient(null);
     }

     public static GetBuilder get(){
         return new GetBuilder();
     }

     public static PostFormBuilder post(){
         return new PostFormBuilder();
     }

     public static PostFileBuilder postFile(){
         return new PostFileBuilder();
     }

     public static PostStringBuilder postString(){
         return new PostStringBuilder();
     }

     public void execute(final RequestCall requestCall, Callback callback) {
         if (callback == null) {
             callback = Callback.CALLBACK_DEFAULT;
         }
         final Callback finalCallback = callback;
         requestCall.getCall().enqueue(new okhttp3.Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                 if (finalCallback == null) {return;}
                 finalCallback.onError(call, e);
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                try{
                    if (call.isCanceled()){
                         finalCallback.onError(call, new IOException("Canceled!"));
                         return;
                    }

                    if (!response.isSuccessful()){
                         finalCallback.onError(call, new IOException("request failed , reponse's code is : " + response.code()));

                         return;
                    }

                    Object obj = finalCallback.parseNetworkResponse(response);
                    finalCallback.onResponse(obj);

                } catch (Exception e){
                        finalCallback.onError(call, e);
                } finally{
                    if (response.body() != null)
                        response.body().close();
                }

            }});
     }
}
