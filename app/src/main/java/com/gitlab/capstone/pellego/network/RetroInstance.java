package com.gitlab.capstone.pellego.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/***************************************************
 *  Chris Bordoy and Arturo Lara
 *
 *  A retrofit instance that establishes the base
 *  url and gson conversion
 **************************************************/

public class RetroInstance {
    public static String BASE_URL = "http://54.245.202.132/";
    private static Retrofit retrofit;

    public static Retrofit getRetroClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
