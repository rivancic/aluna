package com.rivancic.aluna.activities;

import android.os.Bundle;
import android.webkit.WebView;

import com.rivancic.aluna.R;

/**
 * TODO expand image through the whole screen.
 */
public class AboutUsActivity extends BaseActivity {

  //  private ImageView aboutUsIv;
   /* private OnAboutUsImageReceivedListener onAboutUsImageReceivedListener
            = new OnAboutUsImageReceivedListener();*/

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        contentView = R.layout.about_us_activity;
        super.onCreate(savedInstanceState);
        initializeMainFunctionality();
    }

    private void initializeMainFunctionality() {

        webView = (WebView) findViewById(R.id.about_us);
        webView.loadUrl("http://alunaweddings.com/o-naju/");
     //   alunaRepository.getAboutUsImage();
 //       aboutUsIv = (ImageView) findViewById(R.id.about_us_iv);
    }

    @Override
    protected void onResume() {

        super.onResume();
      //  bus.register(onAboutUsImageReceivedListener);
    }

    @Override
    protected void onPause() {

        super.onPause();
      //  bus.unregister(onAboutUsImageReceivedListener);
    }

   /* *//**
     * Display images in image slider
     *//*
    class OnAboutUsImageReceivedListener {

        @Subscribe
        public void getAboutImage(Image aboutUsImage) {

            Timber.i("About us image received in about us activity.");
           *//* Glide
                    .with(AboutUsActivity.this)
                    .load(aboutUsImage.getUrl())
                    .dontTransform().into(aboutUsIv);*//*
        }
    }*/
}
