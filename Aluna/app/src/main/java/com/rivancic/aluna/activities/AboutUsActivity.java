package com.rivancic.aluna.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rivancic.aluna.AlunaApplication;
import com.rivancic.aluna.R;
import com.rivancic.aluna.models.Image;
import com.rivancic.aluna.repositories.AlunaRepository;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import timber.log.Timber;

public class AboutUsActivity extends AppCompatActivity {

    private AlunaApplication application;
    private AlunaRepository alunaRepository;
    private Bus bus;
    private ImageView aboutUsIv;
    private OnAboutUsImageReceivedListener onAboutUsImageReceivedListener
            = new OnAboutUsImageReceivedListener();

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
        aboutUsIv = (ImageView) findViewById(R.id.about_us_iv);
    }

    @Override
    protected void onResume() {

        super.onResume();
        bus.register(onAboutUsImageReceivedListener);
    }

    @Override
    protected void onPause() {

        super.onPause();
        bus.unregister(onAboutUsImageReceivedListener);
    }

    /**
     * Display images in image slider
     */
    class OnAboutUsImageReceivedListener {

        @Subscribe
        public void getAboutImage(Image aboutUsImage) {

            Timber.i("About us image received in about us activity.");
            Glide.with(AboutUsActivity.this).load(aboutUsImage.getUrl()).dontTransform().into(aboutUsIv);
        }
    }
}
