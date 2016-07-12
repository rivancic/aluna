package com.rivancic.aluna.repositories.web;

import com.rivancic.aluna.models.Image;
import com.rivancic.aluna.repositories.AlunaRepository;

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
     * TODO Use some method to push results back to caller
     * @param mainImages
     */
    private void returnMainImagesResult(List<Image> mainImages) {

        // Just to test that we properly parsed the images
        Timber.i("Main images: ");
        for (Image image :
                mainImages) {
            Timber.i(image.toString());
        }
    }
}
