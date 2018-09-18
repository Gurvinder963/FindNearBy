package com.findnearby;

import android.app.Application;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.ProcessLifecycleOwner;

import com.findnearby.di.AppComponent;


public class MyApplication extends Application implements LifecycleObserver {
    private static MyApplication app;
    static AppComponent appComponent;

    public synchronized static MyApplication getInstance() {
        return app;
    }

    public static MyApplication getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = AppComponent.component(this);
        appComponent.inject(this);

        // SHOULD BE UNCOMMENT
        ProcessLifecycleOwner.get().getLifecycle().
              addObserver(this);
    }
}

