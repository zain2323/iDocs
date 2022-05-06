package com.example.idocs.di;

import com.example.idocs.api.iDocsApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppModule {

    private static Retrofit retrofit;
    private static iDocsApi api;

    private static Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(iDocsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient().newBuilder().cookieJar(new SessionCookieJar()).build())
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

    private static class SessionCookieJar implements CookieJar {

        private List<Cookie> cookies;

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            if (url.encodedPath().endsWith("sessions")) {
                this.cookies = new ArrayList<>(cookies);
            }
        }


        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            if (!url.encodedPath().endsWith("account") && cookies != null) {
                return cookies;
            }
            return Collections.emptyList();
        }
    }

}
