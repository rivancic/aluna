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
        assertEquals("List of images should have 12 items", images.size(), 12);
        assertEquals("First image url is not matching", images.get(0).getUrl(), "http://alunaweddings.com/wp-content/uploads/2016/01/avp4374-1000x500.jpg");
        assertEquals("First image url is not matching", images.get(11).getUrl(), "http://alunaweddings.com/wp-content/uploads/2016/01/avp6893-1000x500.jpg");
    }
}