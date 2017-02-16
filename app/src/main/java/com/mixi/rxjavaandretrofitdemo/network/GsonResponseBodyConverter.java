package com.mixi.rxjavaandretrofitdemo.network;

import android.util.Log;

import com.google.gson.Gson;
import com.mixi.rxjavaandretrofitdemo.entity.HttpResult;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by mixi on 2017/2/16.
 */

public class GsonResponseBodyConverter <T>implements Converter<ResponseBody,T> {
    private  final Gson gson;
    private  final Type type;
    GsonResponseBodyConverter(Gson gson,Type type){
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();


        Log.e("mixi","the Thread Name :"+Thread.currentThread().getName());
        HttpResult httpResult = gson.fromJson(response,HttpResult.class);
        if(httpResult.getCount() == 0){
            throw new RuntimeException("the result count is 0");
        }


        return gson.fromJson(response,type);
    }
}
