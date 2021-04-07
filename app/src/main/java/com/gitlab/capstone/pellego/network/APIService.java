package com.gitlab.capstone.pellego.network;

import com.gitlab.capstone.pellego.network.models.AuthToken;
import com.gitlab.capstone.pellego.network.models.LMDescResponse;
import com.gitlab.capstone.pellego.network.models.LMResponse;
import com.gitlab.capstone.pellego.network.models.LibraryResponse;
import com.gitlab.capstone.pellego.network.models.QuizResponse;
import com.gitlab.capstone.pellego.network.models.SMResponse;
import com.gitlab.capstone.pellego.network.models.SynopsisResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/*****************************************************
 * Arturo Lara & Chris Bordoy
 *
 * The API service for all HTTP requests
 *****************************************************/

public interface APIService {

    @POST("modules")
    Call<List<LMResponse>> getModules(@Body AuthToken email);

    @POST("modules/{module_id}/content")
    Call<List<LMDescResponse>> getModuleDescription(@Path("module_id") String mID);

    @POST("modules/{module_id}/submodules")
    Call<List<SMResponse>> getSubmodules(@Path("module_id") String mID);

    @POST("modules/{module_id}/submodules/{submodule_id}/quizzes/{quiz_id}")
    Call<List<QuizResponse>> getQuizzes(@Path("module_id") String mID, @Path("submodule_id") String smID, @Path("quiz_id") String qID);


    // library requests
    @GET("library")
    Call<List<LibraryResponse>> getLibrary();

    @GET("library/synopsis/{book_id}")
    Call<List<SynopsisResponse>> getSynopsis(@Path("book_id") String book_id);


}