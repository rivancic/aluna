package com.rivancic.aluna.activities;

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

/**
 * TODO Instead of text Aluna Weddings show Aluna Weddings logo in ActionBar.
 * TODO fix main images make if keep ratio.
 */
public class MainActivity extends BaseActivity {

    private OnMainImageResponseReceivedListener onMainImageResponseReceived;
    private ContainerView mainSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        contentView = R.layout.main_activity;
        super.onCreate(savedInstanceState);
        initializeMainFunctionality();
    }

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

    @Override
    protected void onStart() {

        super.onStart();
        bus.register(onMainImageResponseReceived);
        alunaRepository.getMainPictures();
    }

    @Override
    protected void onStop() {

        super.onStop();
        bus.unregister(onMainImageResponseReceived);
    }

    /**
     * Display images in image slider
     */
    class OnMainImageResponseReceivedListener {

        @Subscribe
        public void getMainImages(ArrayList<Image> mainImages) {


            Timber.i("Main images handled in main activity.");
            mainSlider.initCardView(new MainSliderContainer(mainImages),
                    R.layout.main_image_slider_item,
                    R.id.sliding_card_content_view);
        }
    }
}
