package com.example.movieslistingmvvm;

import android.app.Application;

public class MyApplication extends Application {

    private static MyApplication mMyApplication;

    public static synchronized MyApplication getInstance(){
        return mMyApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mMyApplication = this;

    }
}
