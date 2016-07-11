package com.rivancic.aluna.models;

/**
 * Created by rivancic on 11/07/16.
 */
public class Image {

    private String name;
    private String url;

    public Image(String url) {

        this.url = url;
    }

    public String getUrl() {

        return url;
    }

    public void setUrl(String url) {

        this.url = url;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    @Override
    public String toString() {

        return "Url: " + url;
    }
}
