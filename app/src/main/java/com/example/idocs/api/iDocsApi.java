package com.example.idocs.api;

import io.appwrite.models.Session;
import io.appwrite.models.User;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface iDocsApi {
    String BASE_URL = "https://f5f0-182-189-247-226.ap.ngrok.io/v1/";

    @FormUrlEncoded
    @POST("account")
    @Headers({"X-Appwrite-Project: 6265f0feac18893637c1"})
    Call<User> createAccount(
            @Field("userId") String userId,
            @Field("email") String email,
            @Field("password") String password,
            @Field("name") String name
    );

    @FormUrlEncoded
    @POST("account/sessions")
    @Headers({"X-Appwrite-Project: 6265f0feac18893637c1"})
    Call<Session> login(
            @Field("email") String email,
            @Field("password") String password
    );


    @GET("account")
    @Headers({"X-Appwrite-Project: 6265f0feac18893637c1"})
    Call<User> getCurrentUser();

    @DELETE("account/sessions/{session_id}")
    @Headers({"X-Appwrite-Project: 6265f0feac18893637c1"})
    Call<Void> logout(@Path("session_id") String sessionId);

    @GET("/v1/account/sessions/oauth2/google")
    @Headers({"X-Appwrite-Project: 6265f0feac18893637c1"})
    Call<Void> oAauthWithGoogle();
}
