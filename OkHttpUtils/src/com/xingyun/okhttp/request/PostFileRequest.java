package com.xingyun.okhttp.request;

import java.io.File;
import java.util.Map;

import com.xingyun.okhttp.OkHttpUtils;
import com.xingyun.okhttp.callback.Callback;
import com.xingyun.okhttp.utils.Exceptions;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PostFileRequest extends OkHttpRequest {
     private static MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");

     private File file;
     private MediaType mediaType;

     public PostFileRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers, File file, MediaType mediaType){
         super(url, tag, params, headers);
         this.file = file;
         this.mediaType = mediaType;

         if (this.file == null){
             Exceptions.illegalArgument("file 不能为 null !");
         }
         if (this.mediaType == null){
             this.mediaType = MEDIA_TYPE_STREAM;
         }
     }

     @Override
     protected RequestBody wrapRequestBody(RequestBody requestBody, final Callback callback){
         if (callback == null) return requestBody;
         CountingRequestBody countingRequestBody = new CountingRequestBody(requestBody, new CountingRequestBody.Listener(){
             @Override
             public void onRequestProgress(final long bytesWritten, final long contentLength){

               callback.inProgress(bytesWritten, contentLength);

             }
         });
         return countingRequestBody;
     }


    @Override
    protected RequestBody buildRequestBody() {

        return RequestBody.create(mediaType, file);
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {

          return builder.post(requestBody).build();
    }

}
