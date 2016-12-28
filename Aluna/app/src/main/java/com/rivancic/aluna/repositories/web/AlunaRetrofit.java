package com.rivancic.aluna.repositories.web;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rivancic.aluna.models.Image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;
import timber.log.Timber;

/**
 * TODO get all the images call next page till there is no next page.
 * Created by rivancic on 28/12/2016.
 */
public class AlunaRetrofit {

    Retrofit retrofit;
    GitHubService service;
    private String imagesSlideshowUrl = "http://alunaweddings.com/wp-admin/admin-ajax.php?id=41&action=pp_api_gallery";

    AlunaRetrofit() {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://alunaweddings.com")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();
        service = retrofit.create(GitHubService.class);
    }

    public List<Image> getImageSlideshow() throws IOException {

        final List<Image> images = new ArrayList<>();
        Call<ImageSlideshow> slideshowImages = service.getSlideshowImages(imagesSlideshowUrl);
        Response<ImageSlideshow> execute = slideshowImages.execute();
        if(execute.isSuccessful()) {
            Timber.i("OK");
            for(Included included: execute.body().getIncluded()) {
                Image image = new Image();
                image.setUrl(included.getAttributes().getSrc());
                images.add(image);
            }
        } else
        {
            Timber.i("Failure");
        }
        return images;
    }

    public interface GitHubService {
        @GET
        Call<ImageSlideshow> getSlideshowImages(@Url String url);
    }


}
