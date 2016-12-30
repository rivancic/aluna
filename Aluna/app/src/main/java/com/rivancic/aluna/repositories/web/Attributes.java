package com.rivancic.aluna.repositories.web;

import java.util.List;

/**
 * Created by rivancic on 28/12/2016.
 */

public class Attributes {

    private static final String XS = "xs";
    List<Sources> sources;
    private String src;

    public Attributes() {

    }

    public List<Sources> getSources() {

        return sources;
    }

    public void setSources(List<Sources> sources) {

        this.sources = sources;
    }

    public String getSrc() {

        return src;
    }

    public void setSrc(String src) {

        this.src = src;
    }

    public String getThumbnailImageUrl() {

        String xsImage = "";
        for (Sources source:
             sources) {
            if(source.getName().equals(XS)) {
                xsImage = source.getUri();
            }
        }
        return xsImage;
    }
}
