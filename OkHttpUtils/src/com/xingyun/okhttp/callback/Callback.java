package com.xingyun.okhttp.callback;

import okhttp3.Call;
import okhttp3.Response;

public abstract class Callback<T> {

    public void inProgress(long uploadLen, long total){

    }


    public abstract T parseNetworkResponse(Response response) throws Exception;

    public abstract void onError(Call call, Exception e);

    public abstract void onResponse(T response);


    public static Callback CALLBACK_DEFAULT = new Callback(){

        @Override
        public Object parseNetworkResponse(Response response) throws Exception{
            return null;
        }

        @Override
        public void onError(Call call, Exception e){

        }

        @Override
        public void onResponse(Object response){

        }
    };
}
