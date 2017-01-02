package com.rivancic.aluna.activities.main;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mxn.soul.slidingcard_core.ContainerView;
import com.mxn.soul.slidingcard_core.SlidingCard;
import com.rivancic.aluna.R;
import com.rivancic.aluna.models.Image;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by rivancic on 02/01/2017.
 */
class MainSliderContainer implements ContainerView.ContainerInterface {

    private Context context;
    private ArrayList<Image> images = new ArrayList<>();

    public MainSliderContainer(Context context) {

        this.context = context;
    }

    public MainSliderContainer(Context context, ArrayList<Image> images) {

        this.context = context ;
        this.images = images;
    }

    public void addImages(List<Image> images) {

        this.images.addAll(images);
        Timber.i("Current images: %d, new images: %d", this.images.size(), images.size() );
    }

    @Override
    public void initCard(SlidingCard card, int index) {

        if(images.size()> index) {
            ImageView sliderIv = (ImageView) card.findViewById(R.id.main_image_slider_iv);
            Image image = images.get(index);
            Glide.with(context).load(image.getUrl()).override(1000, 1000).fitCenter().into(sliderIv);
        }
    }

    @Override
    public void exChangeCard() {

        Image item = images.get(0);
        images.remove(0);
        images.add(item);
    }

    public boolean isEmpty() {

        return images.isEmpty();
    }

    public ArrayList<Image> getImages() {

        return images;
    }
}
