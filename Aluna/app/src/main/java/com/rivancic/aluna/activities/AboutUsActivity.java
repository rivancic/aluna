package com.rivancic.aluna.activities;

import android.os.Bundle;
import android.webkit.WebView;

import com.rivancic.aluna.R;
import com.rivancic.aluna.messages.AboutUsPageContentResult;
import com.squareup.otto.Subscribe;

import timber.log.Timber;

/**
 * TODO expand image through the whole screen.
 */
public class AboutUsActivity extends BaseActivity {

    WebView webView;
    OnAboutUsPageReceivedListener aboutUsPageReceivedListener = new OnAboutUsPageReceivedListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        contentView = R.layout.about_us_activity;
        super.onCreate(savedInstanceState);
        initializeMainFunctionality();
    }

    private void initializeMainFunctionality() {

        webView = (WebView) findViewById(R.id.about_us);
        alunaRepository.getAboutUsPageContent();
    }

    @Override
    protected void onResume() {

        super.onResume();
          bus.register(aboutUsPageReceivedListener);
    }

    @Override
    protected void onPause() {

        super.onPause();
          bus.unregister(aboutUsPageReceivedListener);
    }

   /* *//**
     * Display images in image slider
     */
    class OnAboutUsPageReceivedListener {

        @Subscribe
        public void getAboutImage(AboutUsPageContentResult aboutUsPageContentResult) {

            Timber.i("About us page content received in about us activity.");
            webView.loadData(aboutUsPageContentResult.aboutUsPageContent,  "text/html; charset=utf-8", "UTF-8");
        }
    }
}
