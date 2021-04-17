package com.gitlab.capstone.pellego.network;

import com.gitlab.capstone.pellego.network.models.AllContentsResponse;
import com.gitlab.capstone.pellego.network.models.AuthToken;
import com.gitlab.capstone.pellego.network.models.CompletionResponse;
import com.gitlab.capstone.pellego.network.models.LMDescResponse;
import com.gitlab.capstone.pellego.network.models.LMResponse;
import com.gitlab.capstone.pellego.network.models.LibraryResponse;
import com.gitlab.capstone.pellego.network.models.ProgressValuesResponse;
import com.gitlab.capstone.pellego.network.models.QuizResponse;
import com.gitlab.capstone.pellego.network.models.SMResponse;
import com.gitlab.capstone.pellego.network.models.SynopsisResponse;
import com.gitlab.capstone.pellego.network.models.TotalWordsReadResponse;

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

    @POST("modules/submodule/{submodule_id}/quizzes")
    Call<List<QuizResponse>> getQuizzes(@Path("submodule_id") String smID);

    @POST("modules/allcontent")
    Call<AllContentsResponse> getAllContentsModules(@Body AuthToken token);

    @POST("users/{module_id}/quiz_results/{submodule_id}")
    Call<Void> setSubmoduleCompletion(@Body AuthToken email, @Path("module_id") String mID,
                                      @Path("submodule_id") String smID);

    @POST("users/completion_count")
    Call<List<CompletionResponse>> getUserLearningModulesCompletionCount(@Body AuthToken email);

    @POST("users/{words_read}/{wpm}")
    Call<Void> setUserWordValues(@Body AuthToken email,
                                 @Path("words_read") int wordsRead,
                                 @Path("wpm") int wpm);

    @POST("users/progress_values")
    Call<List<ProgressValuesResponse>> getProgressValues(@Body AuthToken email);

    @GET("library")
    Call<List<LibraryResponse>> getLibrary();

    @GET("library/synopsis/{book_id}")
    Call<List<SynopsisResponse>> getSynopsis(@Path("book_id") String book_id);

    @POST("users/profile/total_words_read")
    Call<TotalWordsReadResponse> getTotalWordsRead(@Body AuthToken email);
}