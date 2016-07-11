package com.rivancic.aluna.repositories;

import android.util.Log;

import com.rivancic.aluna.models.Image;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rivancic on 11/07/16.
 */
public class AlunaWebRepository implements AlunaRepository {

    private static final String TAG = "AlunaWebRepository";

    // TODO extract to properties file
    private static final String WEB_SITE = "http://alunaweddings.com/en/";
    private static final String CSS_IMAGE_QUERY = "#content .rsImg";

    /**
     * Parse a list of images from the main page and convert them in List of {@link com.rivancic.aluna.models.Image}s.
     */
    @Override
    public void getMainPictures() {

        Thread downloadThread = new Thread() {
            public void run() {
                Document doc;
                try {
                    Log.i(TAG, WEB_SITE);
                    doc = Jsoup.connect(WEB_SITE).get();
                    if (doc != null) {
                        List<Image> mainImages = parseMainImagesResult(doc);
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
     * Get image elements from website content
     */
    private List<Image> parseMainImagesResult(Document doc) {

        ArrayList<Image> mainImages = new ArrayList<>();
        Elements mainHtmlImages = doc.select(CSS_IMAGE_QUERY);
        Log.i(TAG, "Images: " + mainHtmlImages.toString());
        String href;
        Image mainImage;
        for (Element mainHtmlImage : mainHtmlImages) {
            href = mainHtmlImage.attr("href");
            Log.i(TAG, "Link href: " + href);
            mainImage = new Image(href);
            mainImages.add(mainImage);
        }
        return mainImages;
    }

    /**
     * TODO Use some method to push results back to caller
     * @param mainImages
     */
    private void returnMainImagesResult(List<Image> mainImages) {

        // Just to test that we properly parsed the images
        Log.i(TAG, "Main images: ");
        for (Image image :
                mainImages) {
            Log.i(TAG, image.toString());
        }
    }
}
