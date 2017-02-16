package com.mixi.rxjavaandretrofitdemo.network;

import com.mixi.rxjavaandretrofitdemo.entity.HttpResult;
import com.mixi.rxjavaandretrofitdemo.entity.Subject;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by mixi on 2017/2/16.
 */

public interface MovieService {

    @GET("top250")
    Observable<HttpResult<List<Subject>>> getTopMovies(@Query("start")int start, @Query("count") int count);
}
