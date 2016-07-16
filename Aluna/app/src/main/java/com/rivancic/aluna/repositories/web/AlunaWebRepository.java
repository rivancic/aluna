package com.rivancic.aluna.repositories.web;

import android.content.Context;
import android.os.Handler;

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

    // TODO extract to properties file
    private static final String WEB_SITE = "http://alunaweddings.com/en/";
    private JsoupParser jsoupParser = new JsoupParser();
    private Bus bus;
    private Context context;

    public AlunaWebRepository(Bus bus, Context context) {

        this.bus = bus;
        this.context = context;
    }

    /**
     * Parse a list of images from the main page and convert them in List of {@link com.rivancic.aluna.models.Image}s.
     */
    @Override
    public void getMainPictures() {

        Thread downloadThread = new Thread() {
            public void run() {
                Document doc;
                try {
                    Timber.i(WEB_SITE);
                    doc = Jsoup.connect(WEB_SITE).get();
                    if (doc != null) {
                        List<Image> mainImages = jsoupParser.parseMainImagesResult(doc);
                        returnMainImagesResult(mainImages);
                    }
                } catch (IOException e) {
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
}
