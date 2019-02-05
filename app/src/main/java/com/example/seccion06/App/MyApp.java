package com.example.seccion06.App;

import android.app.Application;
import android.os.SystemClock;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //MS
        SystemClock.sleep(3000);
    }
}
