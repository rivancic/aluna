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

   // private static final String CSS_IMAGE_QUERY = "#content .rsImg";
    // pp-gallery-image
   private static final String CSS_IMAGE_QUERY = "#pp-gallery-2 .pp-gallery-image";
    private static final String CSS_ABOUT_US_IMAGE_QUERY = ".entry-content.post-content img";

    private static final String CSS_BEST_OF_IMAGE_QUERY = ".post-content img";

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

    /**
     * Get image elements from website content
     */
    public List<Image> parseBestOfImagesResult(Document doc) {

        ArrayList<Image> bestOfImages = new ArrayList<>();
        Elements htmlImages = doc.select(CSS_BEST_OF_IMAGE_QUERY);
        Timber.i("Best OfImages: %s", htmlImages.toString());
        String href;
        Image bestOfImage;
        for (Element image : htmlImages) {
            href = image.attr("src");
            Timber.i("Image src: %s", href);
            bestOfImage = new Image(href);
            bestOfImages.add(bestOfImage);
        }
        return bestOfImages;
    }

    /**
     * Get image elements from website content
     */
    public Image parseAboutUsImageResult(Document doc) {

        Image aboutUsImage = new Image();
        Elements aboutUsImageElements = doc.select(CSS_ABOUT_US_IMAGE_QUERY);
        Timber.i("About us image: %s", aboutUsImage.toString());
        String href;
        //Image mainImage;
        for (Element mainHtmlImage : aboutUsImageElements) {
            href = mainHtmlImage.attr("src");
            Timber.i("Image src: %s", href);
            aboutUsImage = new Image(href);
            //mainImages.add(mainImage);
        }
        return aboutUsImage;
    }
}
