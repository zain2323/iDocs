package com.example.idocs.ui.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.idocs.R;
import com.example.idocs.models.data.User;
import com.google.android.material.textfield.TextInputLayout;

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

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Redirecting to the workspaces screen", Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), signupName.getEditText().getText(), Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(SignupFragmentDirections.actionSignupFragmentToWorkspaceFragment());
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