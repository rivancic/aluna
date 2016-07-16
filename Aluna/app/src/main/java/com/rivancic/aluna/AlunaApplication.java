package com.rivancic.aluna;

import android.app.Application;

import com.rivancic.aluna.repositories.AlunaRepository;
import com.rivancic.aluna.repositories.web.AlunaWebRepository;
import com.squareup.otto.Bus;

import timber.log.Timber;

/**
 * Class used for initializing application wide dependencies.
 * <p/>
 * Initialize Timber with planting the DEBUG tree.
 * Created by rivancic on 12/07/16.
 */
public class AlunaApplication extends Application {

    private Bus bus = new Bus();
    private AlunaRepository alunaRepository;

    public AlunaRepository getAlunaRepository() {

        return alunaRepository;
    }

    public void setAlunaRepository(AlunaRepository alunaRepository) {

        this.alunaRepository = alunaRepository;
    }

    public Bus getBus() {

        return bus;
    }

    public void setBus(Bus bus) {

        this.bus = bus;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        initializeTimber();
        alunaRepository = new AlunaWebRepository(bus, this);
    }

    private void initializeTimber() {

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
