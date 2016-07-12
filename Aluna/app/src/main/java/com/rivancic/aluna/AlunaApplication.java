package com.rivancic.aluna;

import android.app.Application;

import timber.log.Timber;

/**
 * Class used for initializing application wide dependencies.
 *
 * Initialize Timber with planting the DEBUG tree.
 * Created by rivancic on 12/07/16.
 */
public class AlunaApplication extends Application {

    @Override
    public void onCreate() {

        super.onCreate();
        initializeTimber();
    }

    private void initializeTimber() {

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
