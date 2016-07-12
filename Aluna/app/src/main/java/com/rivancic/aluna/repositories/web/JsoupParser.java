package com.rivancic.aluna.repositories.web;


import com.rivancic.aluna.models.Image;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Class that is parsing specific content of the web page with the help of Jsoup library.
 * Created by rivancic on 12/07/16.
 */
public class JsoupParser {

    private static final String CSS_IMAGE_QUERY = "#content .rsImg";

    /**
     * Get image elements from website content
     */
    public List<Image> parseMainImagesResult(Document doc) {

        ArrayList<Image> mainImages = new ArrayList<>();
        Elements mainHtmlImages = doc.select(CSS_IMAGE_QUERY);
        Timber.i("Images: %s", mainHtmlImages.toString());
        String href;
        Image mainImage;
        for (Element mainHtmlImage : mainHtmlImages) {
            href = mainHtmlImage.attr("href");
            Timber.i("Link href: %s", href);
            mainImage = new Image(href);
            mainImages.add(mainImage);
        }
        return mainImages;
    }
}
