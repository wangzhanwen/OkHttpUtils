package com.xingyun.okhttp;

import java.io.File;

import com.xingyun.okhttp.callback.FilleCallback;
import com.xingyun.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.MediaType;

public class Main {

    public static void main(String[] args) {

//
//        OkHttpUtils
//            .get()
//            .url("http://gank.io/api/random/data/Android/20")
//            .addParams("count", "10")
//            .addParams("length", "100")
//            .build()
//            .execute(new StringCallback() {
//
//                @Override
//                public void onResponse(String response) {
//                     System.out.println("response:" + response);
//
//                }
//
//                @Override
//                public void onError(Call call, Exception e) {
//                   System.out.println("error:" + e.getMessage());
//                }
//            });


      //Post键值对表单上传
//      OkHttpUtils.post()
//              .url("https://gank.io/api/add2gank")
//              .addParams("url", "https://www.baidu.com")
//              .addParams("desc", "测试")
//              .addParams("who", "wzw")
//              .addParams("type", "Android")
//              .addParams("debug", "true")
//              .build()
//              .execute(new StringCallback() {
//
//                @Override
//                public void onResponse(String response) {
//                      System.out.println("response:" + response);
//                }
//
//                @Override
//                public void onError(Call call, Exception e) {
//                 System.out.println("error:" + e.getMessage());
//                }
//            });

        //Post表单形式上传文件 + 键值对表单上传
//      OkHttpUtils.post()
//              .url("https://gank.io/api/add2gank")
//              .addParams("url", "https://www.baidu.com")
//              .addParams("desc", "测试")
//              .addParams("who", "wzw")
//              .addParams("type", "Android")
//              .addParams("debug", "true")
//              .addFile("image", "meizhi8.jpeg", new File("/Users/yidong9/Desktop/meizhi8.jpeg"))
//              .build()
//              .execute(new Callback() {
//
//                @Override
//                public void onError(Call call, Exception e) {
//                    System.out.println("error:" + e.getMessage());
//
//                }
//
//                @Override
//                public Object parseNetworkResponse(Response response) throws Exception {
//
//                    return null;
//                }
//
//                @Override
//                public void onResponse(Object response) {
//
//                }
//
//                @Override
//                public void inProgress(long uploadLen, long total) {
//                   System.out.println("uploadLen: " + uploadLen + ",   total: " + total);
//                }
//
//            });

        //Post上传文件
//      OkHttpUtils.postFile()
//              .url("https://gank.io/api/add2gank")
//              .file(new File("/Users/yidong9/Desktop/meizhi8.jpeg"))
//              .build()
//              .execute(new Callback() {
//
//                @Override
//                public void onError(Call call, Exception e) {
//                    System.out.println("error:" + e.getMessage());
//
//                }
//
//                @Override
//                public Object parseNetworkResponse(Response response) throws Exception {
//
//                    return null;
//                }
//
//                @Override
//                public void onResponse(Object response) {
//
//                }
//
//                @Override
//                public void inProgress(long uploadLen, long total) {
//                   System.out.println("uploadLen: " + uploadLen + ",   total: " + total);
//                }
//
//            });

      //下载文件
//      OkHttpUtils.get()
//           .url("http://7xi8d6.com1.z0.glb.clouddn.com/2017-03-13-17265708_396005157434387_3099040288153272320_n.jpg")
//           .build()
//           .execute(new FilleCallback("/Users/yidong9/Desktop", "girl.jpg") {
//
//            @Override
//            public void onResponse(File response) {
//
//
//            }
//
//            @Override
//            public void onError(Call call, Exception e) {
//
//            }
//
//            @Override
//            public void inProgress(long uploadLen, long total) {
//                System.out.println("uploadLen: " + uploadLen + ",   total: " + total);
//            }
//        });

        //上传字符串
        OkHttpUtils
        .postString()
        .url("https://gank.io/api/add2gank")
        .content("{" +
                "    \"error\": true,\n" +
                "    \"msg\": \"地址,描述,你的ID,干货类型都不能为空\"" +
                "}")
         .mediaType(MediaType.parse("application/json; charset=utf-8"))
        .build()
        .execute(new StringCallback() {

            @Override
            public void onResponse(String response) {
                 System.out.println("response:" + response);
            }

            @Override
            public void onError(Call call, Exception e) {
                 System.out.println("error:" + e.getMessage());
            }
        });

    }

}
