package com.example.beaubo.liveat500px;

import android.app.Application;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

/**
 * Created by beaubo on 6/8/2017 AD.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        //Initialize thing(s) here
        Contextor.getInstance().init(getApplicationContext());

    }

    @Override
    public  void onTerminate(){
        super.onTerminate();
    }

}

