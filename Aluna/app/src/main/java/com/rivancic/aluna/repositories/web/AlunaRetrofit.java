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
    //

    AlunaRetrofit() {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://alunaweddings.com")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();
        service = retrofit.create(GitHubService.class);
    }

    public List<Image> getImageSlideshow(String imagesSlideshowUrl) throws IOException {

        final List<Image> images = new ArrayList<>();
        NextSlideshow nextSlideshow = new NextSlideshow();
        nextSlideshow.nextLink = imagesSlideshowUrl;
        do {
            nextSlideshow = getSlideshowPart(images, nextSlideshow.nextLink);
        } while (nextSlideshow.hasNext);
        return images;
    }

    private NextSlideshow getSlideshowPart(List<Image> images, String url) throws IOException {

        NextSlideshow nextSlideshow = new NextSlideshow();
        Call<ImageSlideshow> slideshowImages = service.getSlideshowImages(url);
        Response<ImageSlideshow> execute = slideshowImages.execute();
        if(execute.isSuccessful()) {
            Timber.i("OK");
            List<Included> includedList = execute.body().getIncluded();
            for(Included included: includedList) {
                Image image = new Image();
                image.setUrl(included.getAttributes().getSrc());
                images.add(image);
            }

            // Read next page from included
            if(!includedList.isEmpty()) {
                String next = includedList.get(0).getLinks().getNext();
                if(next != null && !next.isEmpty()) {
                    nextSlideshow.hasNext = true;
                    nextSlideshow.nextLink = next;
                }
            }
        } else
        {
            Timber.i("Failure");
        }
        return nextSlideshow;
    }

    private class NextSlideshow {

        boolean hasNext = false;
        String nextLink = "";
    }

    public interface GitHubService {
        @GET
        Call<ImageSlideshow> getSlideshowImages(@Url String url);
    }


}
