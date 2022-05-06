package com.example.idocs.callbacks;

import io.appwrite.models.User;

public interface GetUserCallback {
    void onSuccess(boolean val, User user);
    void onFailure();
}
