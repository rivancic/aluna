# TODO 

- Properly handle images on first screen they seem skewed now.

- On About us activity nothing is shown on tablet.


09-21 23:31:28.018 9524-9685/com.rivancic.aluna W/OpenGLRenderer: Bitmap too large to be uploaded into a texture (1000x4756, max=4096x4096)

android:layerType="software"
http://stackoverflow.com/questions/18471194/webview-in-scrollview-view-too-large-to-fit-into-drawing-cache-how-to-rewor

- Strech image through whole view
show images in full screen in scrollview android

<ScrollView android:fillViewport="true"
http://stackoverflow.com/questions/20565291/show-images-in-full-screen-in-scrollview-android


- Make AboutUs big as screen

- Sometimes I get an error:

```
09-21 23:23:28.288 29419-29419/com.rivancic.aluna I/BestOfActivity$OnMainImageResponseReceivedListener: Best of images handled in best of activity.
09-21 23:23:28.308 29419-29419/com.rivancic.aluna D/AndroidRuntime: Shutting down VM
09-21 23:23:28.308 29419-29419/com.rivancic.aluna E/AndroidRuntime: FATAL EXCEPTION: main
                                                                    Process: com.rivancic.aluna, PID: 29419
                                                                    java.lang.IllegalArgumentException: You cannot start a load for a destroyed activity
                                                                        at com.bumptech.glide.manager.RequestManagerRetriever.assertNotDestroyed(RequestManagerRetriever.java:134)
                                                                        at com.bumptech.glide.manager.RequestManagerRetriever.get(RequestManagerRetriever.java:102)
                                                                        at com.bumptech.glide.Glide.with(Glide.java:653)
                                                                        at com.rivancic.aluna.activities.BestOfActivity$ThumbnailImageLoader.loadImageThumbnail(BestOfActivity.java:104)
                                                                        at com.rivancic.aluna.activities.CustomImageGalleryFragment.loadImageThumbnail(CustomImageGalleryFragment.java:127)
                                                                        at com.etiennelawlor.imagegallery.library.adapters.ImageGalleryAdapter.onBindViewHolder(ImageGalleryAdapter.java:58)
                                                                        at android.support.v7.widget.RecyclerView$Adapter.onBindViewHolder(RecyclerView.java:5768)
                                                                        at android.support.v7.widget.RecyclerView$Adapter.bindViewHolder(RecyclerView.java:5801)
                                                                        at android.support.v7.widget.RecyclerView$Recycler.getViewForPosition(RecyclerView.java:5037)
                                                                        at android.support.v7.widget.RecyclerView$Recycler.getViewForPosition(RecyclerView.java:4913)
                                                                        at android.support.v7.widget.LinearLayoutManager$LayoutState.next(LinearLayoutManager.java:2029)
                                                                        at android.support.v7.widget.GridLayoutManager.layoutChunk(GridLayoutManager.java:531)
                                                                        at android.support.v7.widget.LinearLayoutManager.fill(LinearLayoutManager.java:1377)
                                                                        at android.support.v7.widget.LinearLayoutManager.onLayoutChildren(LinearLayoutManager.java:578)
                                                                        at android.support.v7.widget.GridLayoutManager.onLayoutChildren(GridLayoutManager.java:170)
                                                                        at android.support.v7.widget.RecyclerView.dispatchLayoutStep2(RecyclerView.java:3260)
                                                                        at android.support.v7.widget.RecyclerView.dispatchLayout(RecyclerView.java:3069)
                                                                        at android.support.v7.widget.RecyclerView.onLayout(RecyclerView.java:3518)
                                                                        at android.view.View.layout(View.java:16768)
                                                                        at android.view.ViewGroup.layout(ViewGroup.java:5333)
                                                                        at android.widget.LinearLayout.setChildFrame(LinearLayout.java:1702)
                                                                        at android.widget.LinearLayout.layoutHorizontal(LinearLayout.java:1691)
                                                                        at android.widget.LinearLayout.onLayout(LinearLayout.java:1467)
                                                                        at android.view.View.layout(View.java:16768)
                                                                        at android.view.ViewGroup.layout(ViewGroup.java:5333)
                                                                        at android.widget.FrameLayout.layoutChildren(FrameLayout.java:573)
                                                                        at android.widget.FrameLayout.onLayout(FrameLayout.java:508)
                                                                        at android.view.View.layout(View.java:16768)
                                                                        at android.view.ViewGroup.layout(ViewGroup.java:5333)
                                                                        at android.widget.LinearLayout.setChildFrame(LinearLayout.java:1702)
                                                                        at android.widget.LinearLayout.layoutHorizontal(LinearLayout.java:1691)
                                                                        at android.widget.LinearLayout.onLayout(LinearLayout.java:1467)
                                                                        at android.view.View.layout(View.java:16768)
                                                                        at android.view.ViewGroup.layout(ViewGroup.java:5333)
                                                                        at android.support.design.widget.HeaderScrollingViewBehavior.layoutChild(HeaderScrollingViewBehavior.java:131)
                                                                        at android.support.design.widget.ViewOffsetBehavior.onLayoutChild(ViewOffsetBehavior.java:42)
                                                                        at android.support.design.widget.AppBarLayout$ScrollingViewBehavior.onLayoutChild(AppBarLayout.java:1319)
                                                                        at android.support.design.widget.CoordinatorLayout.onLayout(CoordinatorLayout.java:817)
                                                                        at android.view.View.layout(View.java:16768)
                                                                        at android.view.ViewGroup.layout(ViewGroup.java:5333)
                                                                        at android.support.v4.widget.DrawerLayout.onLayout(DrawerLayout.java:1191)
                                                                        at android.view.View.layout(View.java:16768)
                                                                        at android.view.ViewGroup.layout(ViewGroup.java:5333)
                                                                        at android.widget.FrameLayout.layoutChildren(FrameLayout.java:573)
                                                                        at android.widget.FrameLayout.onLayout(FrameLayout.java:508)
                                                                        at android.view.View.layout(View.java:16768)
                                                                        at android.view.ViewGroup.layout(ViewGroup.java:5333)
                                                                        at android.widget.LinearLayout.setChildFrame(LinearLayout.java:1702)
                                                                        at android.widget.LinearLayout.layoutVertical(LinearLayout.java:1556)
                                                                        at android.widget.LinearLayout.onLayout(LinearLayout.java:1465)
                                                                        at android.view.View.layout(View.java:16768)
                                                                        at android.view.ViewGroup.layout(ViewGroup.java:5333)
                                                                    	at android.widget.FrameLayout.layoutChildren(FrameLayout.java:5
```


- Implement date chooser in contact activity

- Add facebook logo in menu? or somewhere to open up aluna on facebook page.

- Add references?

# App
- Extract configurations, like website url to configurations..
- Check for internet connection; show proper message if not available

# WebPage
- Watch out the HTML is being manipulated with JavaScript libs.

