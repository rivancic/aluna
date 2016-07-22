package com.rivancic.aluna.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.rivancic.aluna.AlunaApplication;
import com.rivancic.aluna.R;
import com.rivancic.aluna.repositories.AlunaRepository;
import com.squareup.otto.Bus;

public class AboutUsActivity extends AppCompatActivity {

    private AlunaApplication application;
    private AlunaRepository alunaRepository;
    private Bus bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeMainFunctionality();
    }

    private void initializeMainFunctionality() {

        application = (AlunaApplication) getApplication();
        bus = application.getBus();
        alunaRepository = application.getAlunaRepository();
        alunaRepository.getAboutUsImage();
        //onMainImageResponseReceived = new OnMainImageResponseReceivedListener();
        //mainSlider = (ContainerView) findViewById(R.id.main_image_slider);
    }

}
