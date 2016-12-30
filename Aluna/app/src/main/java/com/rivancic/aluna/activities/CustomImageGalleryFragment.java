package com.rivancic.aluna.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.etiennelawlor.imagegallery.library.activities.FullScreenImageGalleryActivity;
import com.etiennelawlor.imagegallery.library.adapters.ImageGalleryAdapter;
import com.etiennelawlor.imagegallery.library.utilities.DisplayUtility;
import com.rivancic.aluna.R;

import java.util.ArrayList;

/**
 * Created by etiennelawlor on 6/10/16.
 */

public class CustomImageGalleryFragment extends Fragment implements ImageGalleryAdapter.OnImageClickListener, ImageGalleryAdapter.ImageThumbnailLoader {

    // region Constants
    public static final String KEY_IMAGES = "KEY_IMAGES";
    public static final String KEY_POSITION = "KEY_POSITION";
    public static final String KEY_TITLE = "KEY_TITLE";
    // endregion

    // region Views
    //private Toolbar toolbar;
    private RecyclerView recyclerView;
    // endregion

    // region Member Variables
    private ArrayList<String> images;
    private String title;
   // private GridSpacesItemDecoration gridSpacesItemDecoration;
    private static ImageGalleryAdapter.ImageThumbnailLoader imageThumbnailLoader;
    // endregion

    // region Constructors
    public CustomImageGalleryFragment() {
    }
    // endregion

    // region Factory Methods
    public static CustomImageGalleryFragment newInstance(Bundle extras) {
        CustomImageGalleryFragment fragment = new CustomImageGalleryFragment();
        fragment.setArguments(extras);
        return fragment;
    }

    public static CustomImageGalleryFragment newInstance() {
        CustomImageGalleryFragment fragment = new CustomImageGalleryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    // endregion

    // region Lifecycle Methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            images = getArguments().getStringArrayList(KEY_IMAGES);
            title = getArguments().getString(KEY_TITLE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.custom_fragment_image_gallery, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindViews();

       // ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

      /*  ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(title);
        }*/

        setUpRecyclerView();

    }
    // endregion

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
       // recyclerView.removeItemDecoration(gridSpacesItemDecoration);
        setUpRecyclerView();
    }

    // region ImageGalleryAdapter.OnImageClickListener Methods
    @Override
    public void onImageClick(int position) {
        Intent intent = new Intent(getContext(), FullScreenImageGalleryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(FullScreenImageGalleryActivity.KEY_IMAGES, images);
        bundle.putInt(FullScreenImageGalleryActivity.KEY_POSITION, position);
        intent.putExtras(bundle);

        startActivity(intent);
    }
    // endregion

    // region ImageGalleryAdapter.ImageThumbnailLoader Methods
    @Override
    public void loadImageThumbnail(ImageView iv, String imageUrl, int dimension) {
        imageThumbnailLoader.loadImageThumbnail(iv, imageUrl, dimension);
    }
    // endregion

    // region Helper Methods
    private void bindViews() {
        recyclerView = (RecyclerView) getActivity().findViewById(com.etiennelawlor.imagegallery.library.R.id.rv);
       // toolbar = (Toolbar) getActivity().findViewById(com.etiennelawlor.imagegallery.library.R.id.toolbar);
    }

    private void setUpRecyclerView() {
        int numOfColumns;
        if (DisplayUtility.isInLandscapeMode(getActivity())) {
            numOfColumns = 4;
        } else {
            numOfColumns = 3;
        }

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numOfColumns));
      //  gridSpacesItemDecoration = new GridSpacesItemDecoration(ImageGalleryUtils.dp2px(getActivity(), 2), numOfColumns);
       // recyclerView.addItemDecoration(gridSpacesItemDecoration);
        ImageGalleryAdapter imageGalleryAdapter = new ImageGalleryAdapter(getContext(), images);
        imageGalleryAdapter.setOnImageClickListener(this);
        imageGalleryAdapter.setImageThumbnailLoader(this);

        recyclerView.setAdapter(imageGalleryAdapter);
    }

    public static void setImageThumbnailLoader(ImageGalleryAdapter.ImageThumbnailLoader loader) {
        imageThumbnailLoader = loader;
    }
    // endregion
}
