package com.rivancic.aluna.repositories.web;

import com.rivancic.aluna.models.Image;

import java.util.ArrayList;

/**
 * Created by rivancic on 02/01/2017.
 */

public interface ReturnListOfImages {
    void returnMainImagesResult(final ArrayList<Image> mainImages);
}
