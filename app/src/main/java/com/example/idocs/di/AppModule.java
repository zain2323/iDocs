package com.example.idocs.di;

import com.example.idocs.api.iDocsApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppModule {

    private static Retrofit retrofit;
    private static iDocsApi api;

    private static Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(iDocsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static iDocsApi providesApi(Retrofit retrofit) {
        return retrofit.create(iDocsApi.class);
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = provideRetrofit();
        }
        return retrofit;
    }

    public static iDocsApi getApi() {
        if (retrofit == null) {
            retrofit = provideRetrofit();
        }
        if (api == null) {
            api = providesApi(retrofit);
        }
        return api;
    }

}
