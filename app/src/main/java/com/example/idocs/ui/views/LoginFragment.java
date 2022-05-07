package com.example.idocs.ui.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.idocs.R;
import com.example.idocs.api.Authentication;
import com.example.idocs.api.iDocsApi;
import com.example.idocs.callbacks.GenericCallback;
import com.example.idocs.di.AppModule;
import com.example.idocs.ui.viewmodel.AppViewModel;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import io.appwrite.Client;
import io.appwrite.exceptions.AppwriteException;
import io.appwrite.models.Session;
import io.appwrite.services.Account;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    private View rootView;
    private TextInputLayout loginEmail;
    private TextInputLayout loginPassword;
    private Button login;
    private Button loginWithGoogle;
    private ProgressBar loginProgressBar;
    private AppViewModel appViewModel;
    iDocsApi api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);
        loginEmail = view.findViewById(R.id.et_login_email);
        loginPassword = view.findViewById(R.id.et_login_password);
        login = view.findViewById(R.id.btn_login);
        loginWithGoogle = view.findViewById(R.id.btn_login_with_google);
        loginProgressBar = view.findViewById(R.id.login_progress_bar);
        api = AppModule.getApi();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = loginEmail.getEditText().getText().toString();
                String password = loginPassword.getEditText().getText().toString();
                boolean isValid = true;
                if (TextUtils.isEmpty(email)) {
                    loginEmail.getEditText().setError("Email can not be empty");
                    isValid = false;
                }
                if (TextUtils.isEmpty(password)) {
                    loginPassword.getEditText().setError("Password can not be empty");
                    isValid = false;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() && !TextUtils.isEmpty(email)) {
                    loginEmail.getEditText().setError("Invalid email");
                    isValid = false;
                }
                if (password.length() < 8 && !TextUtils.isEmpty(password)) {
                    loginPassword.getEditText().setError("Password should be atleast 8 characters long");
                    isValid = false;
                }
                if (!isValid) {
                    return;
                }
                loginProgressBar.setVisibility(View.VISIBLE);
                loginEmail.setEnabled(false);
                loginPassword.setEnabled(false);
                loginWithGoogle.setEnabled(false);
                login.setEnabled(false);
                login(email, password);
            }
        });

        loginWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Not supported yet!!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void login(String email, String password) {
        Call<Session> call = api.login(email, password);
        call.enqueue(new Callback<Session>() {
            @Override
            public void onResponse(Call<Session> call, Response<Session> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(LoginFragment.this.getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                    loginProgressBar.setVisibility(View.INVISIBLE);
                    loginEmail.setEnabled(true);
                    loginPassword.setEnabled(true);
                    loginWithGoogle.setEnabled(true);
                    login.setEnabled(true);
                    return;
                }
                Session session = response.body();
                Toast.makeText(LoginFragment.this.getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(rootView).navigate(LoginFragmentDirections.actionLoginFragmentToWorkspaceFragment());
            }

            @Override
            public void onFailure(Call<Session> call, Throwable t) {
                Toast.makeText(LoginFragment.this.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                loginProgressBar.setVisibility(View.INVISIBLE);
                loginEmail.setEnabled(true);
                loginPassword.setEnabled(true);
                loginWithGoogle.setEnabled(true);
                login.setEnabled(true);
            }
        });
    }
}