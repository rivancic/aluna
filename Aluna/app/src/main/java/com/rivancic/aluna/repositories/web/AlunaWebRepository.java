package com.rivancic.aluna.repositories.web;

import android.content.Context;
import android.os.Handler;

import com.rivancic.aluna.R;
import com.rivancic.aluna.messages.AboutUsPageContentResult;
import com.rivancic.aluna.models.Image;
import com.rivancic.aluna.repositories.AlunaRepository;
import com.squareup.otto.Bus;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

import timber.log.Timber;

/**
 * Created by rivancic on 11/07/16.
 */
public class AlunaWebRepository implements AlunaRepository {

    private static final String TAG = "AlunaWebRepository";
    private static final String ABOUT_US = "o-nama/";
    private static final String BEST_OF = "best-of-weddings/";
    private String mainImagesSlideshowUrl = "http://alunaweddings.com/wp-admin/admin-ajax.php?id=41&action=pp_api_gallery";
    private String bestOfImagesSlideshowUrl = "http://alunaweddings.com/wp-admin/admin-ajax.php?id=839&action=pp_api_gallery";
    private String aboutPage ="http://alunaweddings.com/o-naju/";

    // TODO extract to properties file
    private static String webSite;
    private JsoupParser jsoupParser = new JsoupParser();
    private Bus bus;
    private Context context;

    public AlunaWebRepository(Bus bus, Context context) {

        this.bus = bus;
        this.context = context;
        webSite = context.getString(R.string.host);
    }

    /**
     * Parse a list of images from the main page and convert them in List of {@link com.rivancic.aluna.models.Image}s.
     */
    @Override
    public void getMainPictures() {

        Thread downloadThread = new Thread() {
            public void run() {
                try {
                    AlunaRetrofit alunaRetrofit = new AlunaRetrofit();
                    List<Image> mainImages = alunaRetrofit.getImageSlideshow(mainImagesSlideshowUrl);
                    returnMainImagesResult(mainImages);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        downloadThread.start();
    }

    @Override
    public void getAboutUsImage() {

        Thread downloadThread = new Thread() {
            public void run() {
                Document doc;
                try {
                    String url = webSite + ABOUT_US;
                    Timber.i(url);
                    doc = Jsoup.connect(url).get();
                    if (doc != null) {
                        Image aboutUsImage = jsoupParser.parseAboutUsImageResult(doc);
                        returnAboutUsImageResult(aboutUsImage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        downloadThread.start();
    }

    @Override
    public void getAboutUsPageContent() {

        Thread downloadThread = new Thread() {
            public void run() {
                Document doc;
                try {
                //    String url = webSite + ABOUT_US;
                    Timber.i(aboutPage);
                    doc = Jsoup.connect(aboutPage).get();
                    if (doc != null) {
                        String aboutUsPage = jsoupParser.parseAboutUsPage(doc);

                        // Image aboutUsImage = jsoupParser.parseAboutUsImageResult(doc);
                        returnAboutUsPageResult(aboutUsPage);
                       // returnAboutUsImageResult(aboutUsImage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        downloadThread.start();
    }

    private void returnAboutUsPageResult(String aboutUsPage) {

        // Log response
        Timber.i("About us page: %s", aboutUsPage);
        final AboutUsPageContentResult aboutUsPageContentResult = new AboutUsPageContentResult();
        aboutUsPageContentResult.aboutUsPageContent = aboutUsPage;
        // Get a handler that can be used to post to the main thread
        Handler mainHandler = new Handler(context.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {

                bus.post(aboutUsPageContentResult);
            }
        };
        mainHandler.post(myRunnable);
    }

    @Override
    public void getBestOfImages() {

        Thread downloadThread = new Thread() {
            public void run() {
                try {

                    AlunaRetrofit alunaRetrofit = new AlunaRetrofit();
                    List<Image> mainImages = alunaRetrofit.getImageSlideshow(bestOfImagesSlideshowUrl);
                    returnMainImagesResult(mainImages);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        downloadThread.start();
    }

    /**
     * Send response through Otto bus.
     * @param mainImages list of images on the main page.
     */
    private void returnMainImagesResult(final List<Image> mainImages) {

        // Log response
        Timber.i("Main images: ");
        for (Image image :
                mainImages) {
            Timber.i(image.toString());
        }

        // TODO refactor that
        // Get a handler that can be used to post to the main thread
        Handler mainHandler = new Handler(context.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {

                bus.post(mainImages);
            }
        };
        mainHandler.post(myRunnable);
    }

    /**
     * Send response through Otto bus.
     * @param aboutUsImage image representing about us page.
     */
    private void returnAboutUsImageResult(final Image aboutUsImage) {

        // Log response
        Timber.i("About us image: %s", aboutUsImage.toString());

        // TODO refactor that
        // Get a handler that can be used to post to the main thread
        Handler mainHandler = new Handler(context.getMainLooper());

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {

                bus.post(aboutUsImage);
            }
        };
        mainHandler.post(myRunnable);
    }

}
