package com.example.idocs.api;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.idocs.callbacks.GenericCallback;
import com.example.idocs.callbacks.GetUserCallback;
import com.example.idocs.di.AppModule;
import com.example.idocs.ui.views.WorkspaceFragment;

import io.appwrite.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Authentication {
    private static iDocsApi api = AppModule.getApi();

    public static void getCurrentUser(Context context, GetUserCallback callback) {
        Call<User> call = api.getCurrentUser();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                    Log.e("ERROR", response.code()+"");
                    return;
                }
                User user = response.body();
                Toast.makeText(context, user.getName(), Toast.LENGTH_SHORT).show();
                callback.onSuccess(true, user);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });
    }

    public static void logout(Context context, GenericCallback callback) {
        Call<Void> response = api.logout("current");
        response.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Log.e("ERROR", response.code() + "");
                }
                Toast.makeText(context, "Logout successful", Toast.LENGTH_SHORT).show();
                callback.onSuccess();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
                callback.onFailure();
            }
        });
    }

    public static void oAuthWithGoogle(Context context, GenericCallback callback) {
        Call<Void> call = api.oAauthWithGoogle();
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Log.e("ERROR", response.code() + "");
                }
                Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show();
                callback.onSuccess();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
                callback.onFailure();
            }
        });
    }
}
