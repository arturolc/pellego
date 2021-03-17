package com.gitlab.capstone.pellego.network;

import com.gitlab.capstone.pellego.database.entities.Books;
import com.gitlab.capstone.pellego.network.models.AuthToken;
import com.gitlab.capstone.pellego.network.models.LMDescResponse;
import com.gitlab.capstone.pellego.network.models.LMResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {
//    @GET("library")
//    Call<List<Books>> getBooks();

    @POST("modules")
    Call<List<LMResponse>> getModules(@Body AuthToken email);


    @POST("modules/content/{module_id}")
    Call<List<LMDescResponse>> getModuleDescription(@Path("module_id") String mID);
}
