package com.example.idocs.ui.api;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.example.idocs.ui.views.SignupFragment;
import com.example.idocs.ui.views.SignupFragmentDirections;

import org.jetbrains.annotations.NotNull;

import io.appwrite.Client;
import io.appwrite.exceptions.AppwriteException;
import io.appwrite.models.Session;
import io.appwrite.models.User;
import io.appwrite.services.Account;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

public class Authentication {
    private Account account;
    private Context context;

    public Authentication(Client client, Context context) {
        this.account = new Account(client);
        this.context = context;
    }

    public void login(String email, String password) {
        try {
            account.createSession(
                    email,
                    password,
                    new Continuation<Object>() {
                        @NotNull
                        @Override
                        public CoroutineContext getContext() {
                            return EmptyCoroutineContext.INSTANCE;
                        }

                        @Override
                        public void resumeWith(@NotNull Object o) {
                            String error = "";
                            try {
                                if (o instanceof Result.Failure) {
                                    Result.Failure failure = (Result.Failure) o;
                                    error = failure.exception.getMessage();
                                    throw failure.exception;
                                } else {
                                    Session user = (Session) o;
                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(context,
                                                    "Login Successfully", Toast.LENGTH_LONG).show();
//                                            Navigation.findNavController(view).navigate(LoginFragmentDirections.actionLoginFragmentToWorkspaceFragment());
                                        }
                                    });
                                }
                            } catch (Throwable th) {
                                String message = error;
                                Log.e("ERROR", th.toString());
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(context,
                                                message, Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    }
            );
        } catch (AppwriteException e) {
            e.printStackTrace();
        }
    }

    public void signup(String name, String email, String password) {
        try {
            account.create(
                    "unique()",
                    email,
                    password,
                    name,
                    new Continuation<Object>() {
                        @NotNull
                        @Override
                        public CoroutineContext getContext() {
                            return EmptyCoroutineContext.INSTANCE;
                        }

                        @Override
                        public void resumeWith(@NotNull Object o) {
                            String error = "";
                            try {
                                if (o instanceof Result.Failure) {
                                    Result.Failure failure = (Result.Failure) o;
                                    error = failure.exception.getMessage();
                                    throw failure.exception;
                                } else {
                                    User user = (User) o;
                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(context,
                                                    "Registered Successfully", Toast.LENGTH_LONG).show();
//                                            Navigation.findNavController(view).navigate(SignupFragmentDirections.actionSignupFragmentToWorkspaceFragment());
                                        }
                                    });
                                }
                            } catch (Throwable th) {
                                String message = error;
                                Log.e("ERROR", th.toString());
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(context,
                                                message, Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    }
            );
        } catch (AppwriteException e) {
            e.printStackTrace();
        }
    }
}
