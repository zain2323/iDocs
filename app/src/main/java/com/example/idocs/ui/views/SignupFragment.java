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
import com.example.idocs.models.data.currentUser;
import com.example.idocs.ui.api.AppwriteClient;
import com.example.idocs.ui.api.Authentication;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import io.appwrite.Client;
import io.appwrite.exceptions.AppwriteException;
import io.appwrite.models.User;
import io.appwrite.services.Account;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

public class SignupFragment extends Fragment {

    private TextInputLayout signupName;
    private TextInputLayout signupEmail;
    private TextInputLayout signupPassword;
    private Button signup;
    private Button signupWithGoogle;

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
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signupName = view.findViewById(R.id.et_signup_name);
        signupEmail = view.findViewById(R.id.et_signup_email);
        signupPassword = view.findViewById(R.id.et_signup_password);
        signup = view.findViewById(R.id.btn_signup);
        signupWithGoogle = view.findViewById(R.id.btn_signup_with_google);

        Client client = AppwriteClient.createClient(getContext());
        Account account = new Account(client);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = signupName.getEditText().getText().toString();
                String email = signupEmail.getEditText().getText().toString();
                String password = signupPassword.getEditText().getText().toString();
                boolean isValid = true;
                if (TextUtils.isEmpty(name)) {
                    signupName.getEditText().setError("Name can not be empty");
                    isValid = false;
                }
                if (TextUtils.isEmpty(email)) {
                    signupEmail.getEditText().setError("Email can not be empty");
                    isValid = false;
                }
                if (TextUtils.isEmpty(password)) {
                    signupPassword.getEditText().setError("Password can not be empty");
                    isValid = false;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() && !TextUtils.isEmpty(email)) {
                    signupEmail.getEditText().setError("Invalid email");
                    isValid = false;
                }
                if (password.length() < 8 && !TextUtils.isEmpty(password)) {
                    signupPassword.getEditText().setError("Password should be atleast 8 characters long");
                    isValid = false;
                }
                if (!isValid) {return;}

                Client client = AppwriteClient.createClient(getContext());
                Authentication auth = new Authentication(client, getContext());

                auth.signup(name, email, password);
            }
        });

        signupWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Not supported yet", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(SignupFragmentDirections.actionSignupFragmentToWorkspaceFragment());
            }
        });
    }
}