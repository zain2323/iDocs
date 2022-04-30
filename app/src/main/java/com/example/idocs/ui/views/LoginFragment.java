package com.example.idocs.ui.views;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.idocs.ui.api.AppwriteClient;
import com.example.idocs.ui.api.Authentication;
import com.example.idocs.ui.viewmodel.AppViewModel;
import com.google.android.material.textfield.TextInputLayout;

import io.appwrite.Client;

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

        Client client = AppwriteClient.createClient(getContext());
        Authentication auth = new Authentication(client, getContext(), appViewModel);

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
                auth.login(email, password, loginProgressBar, rootView);

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
}