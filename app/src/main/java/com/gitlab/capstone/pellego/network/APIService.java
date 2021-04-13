package com.gitlab.capstone.pellego.network;

import com.gitlab.capstone.pellego.network.models.AuthToken;
import com.gitlab.capstone.pellego.network.models.CompletionResponse;
import com.gitlab.capstone.pellego.network.models.LMDescResponse;
import com.gitlab.capstone.pellego.network.models.LMResponse;
import com.gitlab.capstone.pellego.network.models.QuizResponse;
import com.gitlab.capstone.pellego.network.models.SMResponse;
import com.gitlab.capstone.pellego.network.models.TodayProgressValueResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
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

    @POST("users/{module_id}/quiz_results/{submodule_id}")
    Call<Void> setSubmoduleCompletion(@Body AuthToken email, @Path("module_id") String mID, @Path("submodule_id") String smID);

    @POST("users/completion_count")
    Call<List<CompletionResponse>> getUserLearningModulesCompletionCount(@Body AuthToken email);

    @POST("users/{words_read}/{wpm}")
    Call<Void> setUserWordValues(@Body AuthToken email,
                                 @Path("words_read") int wordsRead,
                                 @Path("wpm") int wpm);

    @POST("users/today_progress_values")
    Call<TodayProgressValueResponse> getTodayProgressValues(@Body AuthToken email);
}
