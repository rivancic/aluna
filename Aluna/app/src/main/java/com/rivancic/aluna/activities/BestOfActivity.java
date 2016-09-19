package com.rivancic.aluna.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.etiennelawlor.imagegallery.library.ImageGalleryFragment;
import com.etiennelawlor.imagegallery.library.activities.FullScreenImageGalleryActivity;
import com.etiennelawlor.imagegallery.library.activities.ImageGalleryActivity;
import com.etiennelawlor.imagegallery.library.adapters.FullScreenImageGalleryAdapter;
import com.etiennelawlor.imagegallery.library.adapters.ImageGalleryAdapter;
import com.rivancic.aluna.R;
import com.rivancic.aluna.models.Image;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import timber.log.Timber;

/**
 * TODO Make the ActionBar in this activity dark as it shows photos.
 */
public class BestOfActivity extends BaseActivity {

    private OnMainImageResponseReceivedListener onMainImageResponseReceived;
    private CustomImageGalleryFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        contentView = R.layout.best_of_activity;
        super.onCreate(savedInstanceState);
        initializeMainFunctionality();
        getSupportFragmentManager().findFragmentById(android.R.id.content);
        ThumbnailImageLoader thumbnailImageLoader = new ThumbnailImageLoader();
        FullImageLoader fullImageLoader = new FullImageLoader();
        ImageGalleryActivity.setImageThumbnailLoader(thumbnailImageLoader);
        CustomImageGalleryFragment.setImageThumbnailLoader(thumbnailImageLoader);
        FullScreenImageGalleryActivity.setFullScreenImageLoader(fullImageLoader);
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
                fragment = CustomImageGalleryFragment.newInstance(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        //.replace(android.R.id.content, fragment, "")
                        .replace(R.id.image_gallery, fragment, "")
                        .commit();
            } else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .attach(fragment)
                        .commit();
            }
        }
    }

    class ThumbnailImageLoader implements ImageGalleryAdapter.ImageThumbnailLoader {

        @Override
        public void loadImageThumbnail(ImageView iv, String imageUrl, int dimension) {

            Glide.with(BestOfActivity.this).load(imageUrl).into(iv);
        }
    }

    class FullImageLoader implements FullScreenImageGalleryAdapter.FullScreenImageLoader {

        @Override
        public void loadFullScreenImage(ImageView iv, String imageUrl, int width, LinearLayout bglinearLayout) {

            Glide
                    .with(iv.getContext())
                    .load(imageUrl)
                    .override(width, Target.SIZE_ORIGINAL)
                    .dontTransform()
                    .into(iv);
        }
    }
}
