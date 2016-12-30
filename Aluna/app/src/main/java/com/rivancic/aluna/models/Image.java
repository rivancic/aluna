package com.rivancic.aluna.models;

import java.io.Serializable;

/**
 * Created by rivancic on 11/07/16.
 */
public class Image implements Serializable {

    private String name;
    private String url;
    private String thumbnailUrl;

    public Image() {

    }

    public Image(String url) {

        this.url = url;
    }

    public String getThumbnailUrl() {

        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {

        this.thumbnailUrl = thumbnailUrl;
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
