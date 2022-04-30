package com.example.idocs.ui.api;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.example.idocs.ui.viewmodel.AppViewModel;
import com.example.idocs.ui.views.LoginFragment;
import com.example.idocs.ui.views.LoginFragmentDirections;
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
    private AppViewModel appViewModel;

    public Authentication(Client client, Context context, AppViewModel appViewModel) {
        this.account = new Account(client);
        this.context = context;
        this.appViewModel = appViewModel;
    }

    public void login(String email, String password, ProgressBar progressBar, View view) {
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
                                    if (LoginFragment.session_id.equals("")) {
                                        LoginFragment.session_id = user.getId();
                                        LoginFragment.save_session_id(LoginFragment.session_id);
                                    }
                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                          progressBar.setVisibility(View.INVISIBLE);
                                            Toast.makeText(context,
                                                    "Login Successfully", Toast.LENGTH_LONG).show();
                                            Navigation.findNavController(view).navigate(LoginFragmentDirections.actionLoginFragmentToWorkspaceFragment());
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

    public void signup(String name, String email, String password, ProgressBar progressBar) {
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
                                            progressBar.setVisibility(View.INVISIBLE);
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
