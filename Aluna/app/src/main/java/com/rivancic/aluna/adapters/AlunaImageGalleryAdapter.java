package com.rivancic.aluna.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.etiennelawlor.imagegallery.library.utilities.DisplayUtility;

import java.util.List;

/**
 * Created by rivancic on 02/01/2017.
 */

/**
 * Created by etiennelawlor on 8/20/15.
 */
public class AlunaImageGalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // region Member Variables
    private final List<String> images;
    private int gridItemWidth;
    private int gridItemHeight;
    private com.etiennelawlor.imagegallery.library.adapters.ImageGalleryAdapter.OnImageClickListener onImageClickListener;
    private com.etiennelawlor.imagegallery.library.adapters.ImageGalleryAdapter.ImageThumbnailLoader imageThumbnailLoader;
    // endregion

    // region Interfaces
    public interface OnImageClickListener {
        void onImageClick(int position);
    }

    public interface ImageThumbnailLoader {
        void loadImageThumbnail(ImageView iv, String imageUrl, int dimension);
    }
    // endregion

    // region Constructors
    public AlunaImageGalleryAdapter(Context context, List<String> images) {
        this.images = images;

        int screenWidth = DisplayUtility.getScreenWidth(context);
        int numOfColumns;
        if (DisplayUtility.isInLandscapeMode(context)) {
            numOfColumns = 4;
        } else {
            numOfColumns = 3;
        }

        gridItemWidth = screenWidth / numOfColumns;
    }
    // endregion

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(com.etiennelawlor.imagegallery.library.R.layout.image_thumbnail, viewGroup, false);
        v.setLayoutParams(getGridItemLayoutParams(v));

        return new AlunaImageGalleryAdapter.ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final AlunaImageGalleryAdapter.ImageViewHolder holder = (AlunaImageGalleryAdapter.ImageViewHolder) viewHolder;

        String image = images.get(position);

        imageThumbnailLoader.loadImageThumbnail(holder.imageView, image, gridItemWidth);

        holder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = holder.getAdapterPosition();
                if(adapterPos != RecyclerView.NO_POSITION){
                    if (onImageClickListener != null) {
                        onImageClickListener.onImageClick(adapterPos);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (images != null) {
            return images.size();
        } else {
            return 0;
        }
    }

    // region Helper Methods
    public void setOnImageClickListener(com.etiennelawlor.imagegallery.library.adapters.ImageGalleryAdapter.OnImageClickListener listener) {
        this.onImageClickListener = listener;
    }

    public void setImageThumbnailLoader(com.etiennelawlor.imagegallery.library.adapters.ImageGalleryAdapter.ImageThumbnailLoader loader) {
        this.imageThumbnailLoader = loader;
    }

    private ViewGroup.LayoutParams getGridItemLayoutParams(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        layoutParams.width = gridItemWidth;
        layoutParams.height = gridItemWidth;

        return layoutParams;
    }

    public void addImages(List<String> images) {

        this.images.addAll(images);
        notifyDataSetChanged();
    }
    // endregion

    // region Inner Classes

    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        // region Views
        private final ImageView imageView;
        private final FrameLayout frameLayout;
        // endregion

        // region Constructors
        public ImageViewHolder(final View view) {
            super(view);

            imageView = (ImageView) view.findViewById(com.etiennelawlor.imagegallery.library.R.id.iv);
            frameLayout = (FrameLayout) view.findViewById(com.etiennelawlor.imagegallery.library.R.id.fl);
        }
        // endregion
    }

    // endregion
}
