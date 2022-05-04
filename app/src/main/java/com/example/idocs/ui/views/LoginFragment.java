package com.example.idocs.ui.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.idocs.models.data.User;
import com.example.idocs.ui.viewmodel.AppViewModel;
import com.google.android.material.textfield.TextInputLayout;

import io.appwrite.models.Session;
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
    private static SharedPreferences current_user_session_shared_pref;
    public static String session_id;
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
        current_user_session_shared_pref = getActivity().getPreferences(Context.MODE_PRIVATE);
        session_id = read_session_id();
        if (session_id != null) {
            if (!session_id.equals("")) {
                Navigation.findNavController(view).navigate(LoginFragmentDirections.actionLoginFragmentToWorkspaceFragment());
            }
        }
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
                Toast.makeText(getContext(), "Not supported yet", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(LoginFragmentDirections.actionLoginFragmentToWorkspaceFragment());
            }
        });
    }

    public static void save_session_id(String session_id) {
        SharedPreferences.Editor editor = current_user_session_shared_pref.edit();
        editor.putString("COM.EXAMPLE.IDOCS.UI.VIEWS.LOGIN_FRAGMENT_CURRENT_USER_SESSION_ID", session_id);
        editor.apply();
    }

    public static String read_session_id() {
        String session_id = current_user_session_shared_pref.getString("COM.EXAMPLE.IDOCS.UI.VIEWS.LOGIN_FRAGMENT_CURRENT_USER_SESSION_ID", "");
        return session_id;
    }


    public void getUserById(EditText textView) {
        Call<User> call = api.getUserById("6269e9508838e69bb7ca");
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    textView.setText("Code: " + response.code());
                    return;
                }

                User user = response.body();
                String content = "";
                content += "ID: " + user.getId();
                content += "Name: " + user.getName();
                content += "Email: " + user.getEmail();
                textView.setText(content);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

    public void login(String email, String password) {
        Call<Session> call = api.login(email, password);
        call.enqueue(new Callback<Session>() {
            @Override
            public void onResponse(Call<Session> call, Response<Session> response) {
                if (!response.isSuccessful()) {
                    Log.i("ERROR BODY", response.raw().message());
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
            }
        });
    }

}