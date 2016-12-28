package com.rivancic.aluna.repositories.web;

import java.util.List;

/**
 * Just mapper for REST API presentation of images.
 */
public class ImageSlideshow {

    public ImageSlideshow(){}

    private List<Included> included;

    public List<Included> getIncluded() {

        return included;
    }

    public void setIncluded(List<Included> included) {

        this.included = included;
    }


}
