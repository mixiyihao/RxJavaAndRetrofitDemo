package com.mixi.rxjavaandretrofitdemo.network;

import com.mixi.rxjavaandretrofitdemo.entity.HttpResult;
import com.mixi.rxjavaandretrofitdemo.entity.Subject;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by mixi on 2017/2/16.
 *
 *
 */

public class HttpRequestFactory {
    private final int DEFAULT_TIMEOUT = 5;
    private Retrofit mRetrofit;
    private MovieService mMovieService;
    private final String BASEURL="https://api.douban.com/v2/movie/";
    private HttpRequestFactory(){
        //手动创建OKHttpClient 并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(ResponseConvertFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASEURL)
                .build();
        mMovieService = mRetrofit.create(MovieService.class);
    }
    private static HttpRequestFactory mNetWorkFactor;

    public static HttpRequestFactory getIntance(){
        if(mNetWorkFactor == null){
            synchronized (HttpRequestFactory.class){
                if(mNetWorkFactor == null){
                    mNetWorkFactor = new HttpRequestFactory();
                }
            }
        }
        return mNetWorkFactor;
    }

    /**
     * 获取信息
     * @param subscriber
     * @param start
     * @param count
     */
    public void getTopMovies(Subscriber<List<Subject>> subscriber, int start, int count){
        Observable observable = mMovieService.getTopMovies(start,count).map(new HttpResutlFunc());
        toSubscribe(observable,subscriber);
    }

    private <T> void toSubscribe(Observable<T> o,Subscriber<T> subscriber){
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    private class HttpResutlFunc<T> implements Func1<HttpResult<T>,T>{


        @Override
        public T call(HttpResult<T> httpResult) {

            if(httpResult == null ||httpResult.getCount() == 0){
                throw new RuntimeException("the result is 0");
            }
            return httpResult.getData();
        }
    }


}
