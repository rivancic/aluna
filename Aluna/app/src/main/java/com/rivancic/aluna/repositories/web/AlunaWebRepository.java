package com.rivancic.aluna.repositories.web;

import android.content.Context;
import android.os.Handler;

import com.rivancic.aluna.R;
import com.rivancic.aluna.messages.AboutUsPageContentResult;
import com.rivancic.aluna.messages.MainImagesResult;
import com.rivancic.aluna.models.Image;
import com.rivancic.aluna.repositories.AlunaRepository;
import com.squareup.otto.Bus;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

import timber.log.Timber;

/**
 * Created by rivancic on 11/07/16.
 */
public class AlunaWebRepository implements AlunaRepository {

    private static final String TAG = "AlunaWebRepository";
    private static final String ABOUT_US = "o-nama/";
    private static final String BEST_OF = "best-of-weddings/";
    // TODO extract to properties file
    private static String webSite;
    private String mainImagesSlideshowUrl = "http://alunaweddings.com/wp-admin/admin-ajax.php?id=41&action=pp_api_gallery";
    private String bestOfImagesSlideshowUrl = "http://alunaweddings.com/wp-admin/admin-ajax.php?id=839&action=pp_api_gallery";
    private String aboutPage = "http://alunaweddings.com/o-naju/";
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
                    ArrayList<Image> mainImages = alunaRetrofit.getImageSlideshow(mainImagesSlideshowUrl, new ReturnMainImages());
                    //returnMainImagesResult(mainImages);
                } catch (Exception e) {
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
                    Timber.i(aboutPage);
                    doc = Jsoup.connect(aboutPage).get();
                    if (doc != null) {
                        String aboutUsPage = jsoupParser.parseAboutUsPage(doc);

                        returnAboutUsPageResult(aboutUsPage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        downloadThread.start();
    }

    @Override
    public void getBestOfImages() {

        Thread downloadThread = new Thread() {
            public void run() {

                try {

                    AlunaRetrofit alunaRetrofit = new AlunaRetrofit();
                    ArrayList<Image> mainImages = alunaRetrofit.getImageSlideshow(bestOfImagesSlideshowUrl, new ReturnBestOfImages());
                  //  returnMainImagesResult(mainImages);
                } catch (Exception e) {
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

    class ReturnMainImages implements ReturnListOfImages {

        /**
         * Send response through Otto bus.
         *
         * @param mainImages list of images on the main page.
         */
        public void returnMainImagesResult(final ArrayList<Image> mainImages) {

            // Log response
            Timber.i("Main images: ");
            for (Image image :
                    mainImages) {
                Timber.i(image.toString());
            }

            final MainImagesResult mainImagesResult = new MainImagesResult();
            mainImagesResult.setMainImages(mainImages);

            // TODO refactor that
            // Get a handler that can be used to post to the main thread
            Handler mainHandler = new Handler(context.getMainLooper());
            Runnable myRunnable = new Runnable() {
                @Override
                public void run() {

                    bus.post(mainImagesResult);
                }
            };
            mainHandler.post(myRunnable);
        }
    }

    class ReturnBestOfImages implements ReturnListOfImages {

        /**
         * Send response through Otto bus.
         *
         * @param mainImages list of images on the main page.
         */
        public void returnMainImagesResult(final ArrayList<Image> mainImages) {

            // Log response
            Timber.i("Main images: ");
            for (Image image :
                    mainImages) {
                Timber.i(image.toString());
            }

            //final MainImagesResult mainImagesResult = new MainImagesResult();
            //mainImagesResult.setMainImages(mainImages);

            // TODO refactor that
            // Get a handler that can be used to post to the main thread
            final Handler mainHandler = new Handler(context.getMainLooper());
            Runnable myRunnable = new Runnable() {
                @Override
                public void run() {

                    bus.post(mainImages);
                }
            };
            mainHandler.post(myRunnable);
        }
    }

}
