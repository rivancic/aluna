package com.rivancic.aluna.activities;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.etiennelawlor.imagegallery.library.ImageGalleryFragment;
import com.etiennelawlor.imagegallery.library.activities.ImageGalleryActivity;
import com.etiennelawlor.imagegallery.library.adapters.ImageGalleryAdapter;
import com.rivancic.aluna.R;
import com.rivancic.aluna.models.Image;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import timber.log.Timber;

public class BestOfActivity extends BaseActivity {

    private OnMainImageResponseReceivedListener onMainImageResponseReceived;
    private ImageGalleryFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        contentView = R.layout.best_of_activity;
        super.onCreate(savedInstanceState);
        initializeMainFunctionality();

        getSupportFragmentManager().findFragmentById(android.R.id.content);

    }

    private void initializeMainFunctionality() {

        onMainImageResponseReceived = new OnMainImageResponseReceivedListener();
    }

    @Override
    protected void onStart() {

        super.onStart();
        bus.register(onMainImageResponseReceived);
        alunaRepository.getBestOfImages();
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

            ArrayList<String> imageURLs = new ArrayList<>();
            for (Image image :
                    mainImages) {
                imageURLs.add(image.getUrl());
            }

            Bundle bundle = new Bundle();
            bundle.putStringArrayList(ImageGalleryFragment.KEY_IMAGES, imageURLs);
            bundle.putString(ImageGalleryActivity.KEY_TITLE, "Unsplash Images");

            Timber.i("Best of images handled in best of activity.");
            if (fragment == null) {
                fragment = ImageGalleryFragment.newInstance(bundle);
                fragment.setImageThumbnailLoader(new ImageLoader());
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(android.R.id.content, fragment, "")
                        .commit();
            } else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .attach(fragment)
                        .commit();
            }
        }
    }

    class ImageLoader implements ImageGalleryAdapter.ImageThumbnailLoader {

        @Override
        public void loadImageThumbnail(ImageView iv, String imageUrl, int dimension) {

            Glide.with(BestOfActivity.this).load(imageUrl).into(iv);
        }
    }
}
