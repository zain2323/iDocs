package com.example.idocs.ui.views;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.idocs.R;
import com.example.idocs.ui.api.AppwriteClient;
import com.example.idocs.ui.api.Authentication;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.appwrite.Client;
import io.appwrite.exceptions.AppwriteException;
import io.appwrite.models.Session;
import io.appwrite.services.Account;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

public class LoginFragment extends Fragment {

    private TextInputLayout loginEmail;
    private TextInputLayout loginPassword;
    private Button login;
    private Button loginWithGoogle;
    private String error;

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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginEmail = view.findViewById(R.id.et_login_email);
        loginPassword = view.findViewById(R.id.et_login_password);
        login = view.findViewById(R.id.btn_login);
        loginWithGoogle = view.findViewById(R.id.btn_login_with_google);

        Client client = AppwriteClient.createClient(getContext());
        Authentication auth = new Authentication(client, getContext());

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

                auth.login(email, password);
            }
        });

        loginWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Not supported yet", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(LoginFragmentDirections.actionLoginFragmentToWorkspaceFragment());
            }
        });
    }
}