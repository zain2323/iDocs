package com.example.idocs.ui.views;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.idocs.api.iDocsApi;
import com.example.idocs.di.AppModule;
import com.example.idocs.ui.viewmodel.AppViewModel;
import com.google.android.material.textfield.TextInputLayout;

import io.appwrite.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupFragment extends Fragment {

    private View rootView;
    private TextInputLayout signupName;
    private TextInputLayout signupEmail;
    private TextInputLayout signupPassword;
    private Button signup;
    private Button signupWithGoogle;
    private ProgressBar signupProgressBar;
    private AppViewModel appViewModel;
    private iDocsApi api;

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
        rootView = inflater.inflate(R.layout.fragment_signup, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);
        api = AppModule.getApi();
        signupName = view.findViewById(R.id.et_signup_name);
        signupEmail = view.findViewById(R.id.et_signup_email);
        signupPassword = view.findViewById(R.id.et_signup_password);
        signup = view.findViewById(R.id.btn_signup);
        signupWithGoogle = view.findViewById(R.id.btn_signup_with_google);
        signupProgressBar = view.findViewById(R.id.signup_progress_bar);
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
                if (!isValid) {
                    return;
                }

                signupProgressBar.setVisibility(View.VISIBLE);
                signupName.setEnabled(false);
                signupEmail.setEnabled(false);
                signupPassword.setEnabled(false);
                signup.setEnabled(false);
                signupWithGoogle.setEnabled(false);
                createAccount(email, password, name);
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

    public void createAccount(String email, String password, String name) {
        Call<User> call = api.createAccount("unique()", email, password, name);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(SignupFragment.this.getContext(), "Registration Failed", Toast.LENGTH_LONG).show();
                    signupProgressBar.setVisibility(View.INVISIBLE);
                    signupName.setEnabled(true);
                    signupEmail.setEnabled(true);
                    signupPassword.setEnabled(true);
                    signup.setEnabled(true);
                    signupWithGoogle.setEnabled(true);
                    return;
                }
                Toast.makeText(SignupFragment.this.getContext(), "Account has been created", Toast.LENGTH_SHORT).show();
                signupProgressBar.setVisibility(View.INVISIBLE);
                Navigation.findNavController(rootView).navigate(SignupFragmentDirections.actionSignupFragmentToWorkspaceFragment());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(SignupFragment.this.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                signupProgressBar.setVisibility(View.INVISIBLE);
                signupName.setEnabled(true);
                signupEmail.setEnabled(true);
                signupPassword.setEnabled(true);
                signup.setEnabled(true);
                signupWithGoogle.setEnabled(true);
            }
        });
    }
}