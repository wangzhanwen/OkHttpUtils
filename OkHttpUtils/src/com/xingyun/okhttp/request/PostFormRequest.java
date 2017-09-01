package com.xingyun.okhttp.request;

import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import com.xingyun.okhttp.OkHttpUtils;
import com.xingyun.okhttp.builder.PostFormBuilder;
import com.xingyun.okhttp.callback.Callback;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PostFormRequest extends OkHttpRequest{
    private List<PostFormBuilder.FileInput> files;

    public PostFormRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers,  List<PostFormBuilder.FileInput> files) {
        super(url, tag, params, headers);
        this.files = files;
    }

    @Override
    protected RequestBody buildRequestBody() {
        if (files == null || files.isEmpty()) {
            FormBody.Builder builder = new FormBody.Builder();
            //若没有文件表单，添加键值对表单
            addParams(builder);
            FormBody formBody = builder.build();
            return formBody;
        }else {
            //若有文件 构建复杂的请求体，多块请求体中每块请求都是一个请求体
             MultipartBody.Builder builder = new MultipartBody.Builder()
                     .setType(MultipartBody.FORM);
             addParams(builder);
             for(int i = 0; i < files.size(); i++) {
                 PostFormBuilder.FileInput fileInput = files.get(i);
                 RequestBody fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileInput.filename)), fileInput.file);
                 builder.addFormDataPart(fileInput.key, fileInput.filename, fileBody);
             }
           return builder.build();
        }

    }

    private String guessMimeType(String filename) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = null;
        try{
            contentTypeFor = fileNameMap.getContentTypeFor(URLEncoder.encode(filename, "UTF-8"));
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        if (contentTypeFor == null){
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    /**
     * 构建复杂的请求体
     *   MultipartBody.Builder 可以构建复杂的请求体，与HTML文件上传形式兼容。
     *   多块请求体中每块请求都是一个请求体，可以定义自己的请求头。
     *   这些请求头可以用来描述这块请求，例如他的 Content-Disposition 。
     *   如果 Content-Length 和 Content-Type 可用的话，他们会被自动添加到请求头
     */
    private void addParams(MultipartBody.Builder builder) {
        if (params !=null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + key + "\""),
                                 RequestBody.create(null, params.get(key)));
            }
        }

     }


    /**
     * 添加键值对表单
     * @param builder
     */
    private void addParams(FormBody.Builder builder) {
       if (params != null) {
        for (String key : params.keySet()) {
            builder.add(key, params.get(key));
        }
       }

    }

    @Override
    protected RequestBody wrapRequestBody(RequestBody requestBody, final Callback callback){
        if (callback == null)
           return requestBody;
        CountingRequestBody countingRequestBody = new CountingRequestBody(requestBody, new CountingRequestBody.Listener(){
            @Override
            public void onRequestProgress(final long bytesWritten, final long contentLength){

                  callback.inProgress(bytesWritten, contentLength);

            }
        });
        return countingRequestBody;
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {

        return builder.post(requestBody).build();
    }

}
