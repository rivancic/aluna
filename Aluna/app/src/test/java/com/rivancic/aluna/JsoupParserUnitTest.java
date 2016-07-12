package com.rivancic.aluna;

import com.rivancic.aluna.models.Image;
import com.rivancic.aluna.repositories.web.JsoupParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class JsoupParserUnitTest {

    @Test
    public void getMainImages() throws Exception {

        JsoupParser jsoupParser = new JsoupParser();
        Document parse = Jsoup.parse(WebPageContent.MAIN_PAGE_CONTENT);
        List<Image> images = jsoupParser.parseMainImagesResult(parse);
        assertNotNull("List of images should not be null", images);
        assertEquals("List of images should have X items", images.size(), 10);
        assertEquals("First image url is not matching", images.get(0), "");
        assertEquals("First image url is not matching", images.get(9).getUrl(), "");
    }
}