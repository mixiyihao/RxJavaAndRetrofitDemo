package com.mixi.rxjavaandretrofitdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mixi.rxjavaandretrofitdemo.entity.Subject;
import com.mixi.rxjavaandretrofitdemo.network.HttpRequestFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Subscriber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttpRequestFactory.getIntance().getTopMovies(new Subscriber<List<Subject>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(List<Subject> subjects) {
                Log.e("mixia",Thread.currentThread().getName());
                Log.e("mixi",""+ Arrays.toString(subjects.toArray()));
            }
        },0,10);

    }
}
