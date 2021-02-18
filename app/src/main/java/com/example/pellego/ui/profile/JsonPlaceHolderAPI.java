package com.example.pellego.ui.profile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderAPI {
    //posts is the relative url
    //we will pass in the base url somewhere else
    //http://jsonplaceholder.typicode.com/posts
    @GET("posts")
    Call<List<PostObject>> getPosts();

    //user is the relative url for our username endpoint
    @GET("user")
    Call<UserObject> getUserName();
}
