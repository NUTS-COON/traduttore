package ru.nuts_coon.traduttore;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Michael on 15.08.2017.
 * Для перевода используется сервис «Яндекс.Переводчик»
 */

interface Api {

    @GET("/api/v1.5/tr.json/translate")
    Call<Answer> getAnswer(@Query("key") String key, @Query("text") String text, @Query("lang") String lang);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://translate.yandex.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
