package com.rivancic.aluna.activities.main;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.mxn.soul.slidingcard_core.ContainerView;
import com.rivancic.aluna.R;
import com.rivancic.aluna.activities.BaseActivity;
import com.rivancic.aluna.messages.MainImagesResult;
import com.rivancic.aluna.models.Image;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import timber.log.Timber;

/**
 * TODO Instead of text Aluna Weddings show Aluna Weddings logo in ActionBar.
 * TODO fix main images make if keep ratio.
 * TODO Set fonts
 */
public class MainActivity extends BaseActivity {

    private OnMainImageResponseReceivedListener onMainImageResponseReceived;
    private ContainerView mainSlider;
    private boolean isRestarted = false;
   // private ArrayList<Image> mainImages = new ArrayList<>();
    private ProgressBar progressBar;
    private static final String IMAGES = "IMAGES";
    private MainSliderContainer mainSliderContainer = new MainSliderContainer(this);

    private void initializeMainFunctionality() {

        onMainImageResponseReceived = new OnMainImageResponseReceivedListener();
        mainSlider = (ContainerView) findViewById(R.id.main_image_slider);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    /**
     * Display images in image slider
     */
    class OnMainImageResponseReceivedListener {

        @Subscribe
        public void getMainImages(MainImagesResult mainImages) {

           // MainActivity.this.mainImages.addAll(mainImages.getMainImages());
            Timber.i("Main images handled in main activity.");
            addImagesToCartSlider(mainImages.getMainImages());
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putSerializable(IMAGES, mainSliderContainer.getImages());
        super.onSaveInstanceState(outState);
    }

    private void initCardSlider(ArrayList<Image> mainImages) {

        mainSliderContainer = new MainSliderContainer(this, mainImages);
        mainSlider.initCardView(mainSliderContainer,
                R.layout.main_image_slider_item,
                R.id.sliding_card_content_view);
    }

    private void addImagesToCartSlider(ArrayList<Image> mainImages) {

        boolean load = false;
        if(mainSliderContainer.isEmpty()) {
            load = true;
        }
        mainSliderContainer.addImages(mainImages);
        if(load) {
            mainSlider.initCardView(mainSliderContainer, R.layout.main_image_slider_item, R.id.sliding_card_content_view);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        contentView = R.layout.main_activity;
        super.onCreate(savedInstanceState);
        initializeMainFunctionality();
        if (savedInstanceState != null) {
            isRestarted = true;
            ArrayList<Image> mainImages = (ArrayList<Image>) savedInstanceState.getSerializable(IMAGES);
            if(mainImages != null) {
                initCardSlider(mainImages);
            } else {
                initCardSlider(new ArrayList<Image>());
            }
        }
        Timber.i("onCreate");
    }

    @Override
    protected void onRestart() {

        super.onRestart();
        isRestarted = true;
        Timber.i("onRestart");
    }

    @Override
    protected void onStart() {

        super.onStart();
        bus.register(onMainImageResponseReceived);
        Timber.i("onStart");
        if (!isRestarted) {
            alunaRepository.getMainPictures();
        }
        isRestarted = false;
    }

    @Override
    protected void onStop() {

        super.onStop();
        try {
            bus.unregister(onMainImageResponseReceived);
        } catch (Exception e) {
            Timber.i("Nothing to unregister");
        }
    }
}
