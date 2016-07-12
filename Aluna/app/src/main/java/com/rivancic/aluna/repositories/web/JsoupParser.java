package com.rivancic.aluna.repositories.web;

import android.util.Log;

import com.rivancic.aluna.models.Image;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO Replace usage of Log with Timber so we can test core classes with Unit test.
 * Created by rivancic on 12/07/16.
 */
public class JsoupParser {

    private static final String TAG = "JsoupParser";
    private static final String CSS_IMAGE_QUERY = "#content .rsImg";

    /**
     * TODO test
     * Get image elements from website content
     */
    public List<Image> parseMainImagesResult(Document doc) {

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
}
