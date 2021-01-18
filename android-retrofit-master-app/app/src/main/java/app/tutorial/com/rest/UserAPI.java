package app.tutorial.com.rest;

import app.tutorial.com.model.demoapp.UserResponseParser;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class UserAPI {
    private static final String key = "10";
    private static final String BaseURl = "https://randomuser.me/api/";

    //https://randomuser.me/api/?results=10

    public static PostService postService = null;

    public static PostService getPostService() {
        if (postService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BaseURl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            postService= retrofit.create(PostService.class);
        }
        return postService;
    }

    public interface PostService {

        @GET("?results=" + key)
        Call<UserResponseParser> getResParser();

    }
}
