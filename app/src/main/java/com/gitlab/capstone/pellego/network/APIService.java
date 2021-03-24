package com.gitlab.capstone.pellego.network;

import com.gitlab.capstone.pellego.database.entities.Books;
import com.gitlab.capstone.pellego.network.models.AuthToken;
import com.gitlab.capstone.pellego.network.models.LMDescResponse;
import com.gitlab.capstone.pellego.network.models.LMResponse;
import com.gitlab.capstone.pellego.network.models.QuizResponse;
import com.gitlab.capstone.pellego.network.models.SMResponse;

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


    @POST("modules/{module_id}/content")
    Call<List<LMDescResponse>> getModuleDescription(@Path("module_id") String mID);

    @POST("modules/{module_id}/submodules")
    Call<List<SMResponse>> getSubmodules(@Path("module_id") String mID);

    @POST("modules/{module_id}/submodules/{submodule_id}/quizzes")
    Call<List<QuizResponse>> getQuizzes(@Path("module_id") String mID, @Path("submodule_id") String smID);
}
