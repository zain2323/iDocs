package com.example.idocs.api;

import com.example.idocs.models.data.User;

import io.appwrite.models.Session;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface iDocsApi {
    String BASE_URL = "https://95e9-182-189-243-110.ap.ngrok.io/v1/";

    @GET("users/{user_id}")
    @Headers({"X-Appwrite-Key: b8d6159a9da96ded63d50efa62b836862cd9a8e0001156741a24d19e004aaf374d309761255cfa69aa033cb3137898d19919833a03b3ffad03e3630ea59c3dc1346509f01859cd20bec37c990992bb8822501000fea17a3fcf1e954f7fbbd2f4b85ea32ccfa7820900f524a34097fb6b240746cbc410508bc0a315685b1d26ad"
            ,"X-Appwrite-Project: 6265f0feac18893637c1"})
    Call<User> getUserById(@Path("user_id") String userId);

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
}
