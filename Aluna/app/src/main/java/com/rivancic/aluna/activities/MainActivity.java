package com.rivancic.aluna.activities;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mxn.soul.slidingcard_core.ContainerView;
import com.mxn.soul.slidingcard_core.SlidingCard;
import com.rivancic.aluna.R;
import com.rivancic.aluna.models.Image;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * TODO Instead of text Aluna Weddings show Aluna Weddings logo in ActionBar.
 * TODO fix main images make if keep ratio.
 * TODO Set fonts
 */
public class MainActivity extends BaseActivity {

    private OnMainImageResponseReceivedListener onMainImageResponseReceived;
    private ContainerView mainSlider;
    private boolean isRestarted = false;
    private ArrayList<Image> mainImages;
    private static final String IMAGES = "IMAGES";

    private void initializeMainFunctionality() {

        onMainImageResponseReceived = new OnMainImageResponseReceivedListener();
        mainSlider = (ContainerView) findViewById(R.id.main_image_slider);
    }

    class MainSliderContainer implements ContainerView.ContainerInterface {

        private List<Image> images;

        public MainSliderContainer(List<Image> images) {

            this.images = images;
        }

        @Override
        public void initCard(SlidingCard card, int index) {

            ImageView sliderIv = (ImageView) card.findViewById(R.id.main_image_slider_iv);
            Image image = images.get(index);
            Glide.with(MainActivity.this).load(image.getUrl()).override(1000, 1000).fitCenter().into(sliderIv);
        }

        @Override
        public void exChangeCard() {

            Image item = images.get(0);
            images.remove(0);
            images.add(item);
        }
    }

    /**
     * Display images in image slider
     */
    class OnMainImageResponseReceivedListener {

        @Subscribe
        public void getMainImages(ArrayList<Image> mainImages) {

            MainActivity.this.mainImages = mainImages;
            Timber.i("Main images handled in main activity.");
            initCardSlider(mainImages);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putSerializable(IMAGES, mainImages);
        super.onSaveInstanceState(outState);
    }

    private void initCardSlider(ArrayList<Image> mainImages) {

        mainSlider.initCardView(new MainSliderContainer(mainImages),
                R.layout.main_image_slider_item,
                R.id.sliding_card_content_view);
    }

    @Override
    protected void attachBaseContext(Context newBase) {

        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        contentView = R.layout.main_activity;
        super.onCreate(savedInstanceState);
        initializeMainFunctionality();
        if (savedInstanceState != null) {
            isRestarted = true;
            mainImages = (ArrayList<Image>) savedInstanceState.getSerializable(IMAGES);
            if(mainImages != null) {
                initCardSlider(mainImages);
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
