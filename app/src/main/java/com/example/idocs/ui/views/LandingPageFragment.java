package com.example.idocs.ui.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.idocs.R;
import com.example.idocs.api.Authentication;
import com.example.idocs.callbacks.GetUserCallback;

import io.appwrite.models.User;

public class LandingPageFragment extends Fragment {

    private Button login;
    private Button signup;
    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GetUserCallback callback = new GetUserCallback() {
            @Override
            public void onSuccess(boolean val, User user) {
                if (val) {
                    Navigation.findNavController(rootView).navigate(LandingPageFragmentDirections.actionLandingPageFragmentToWorkspaceFragment());
                }
            }

            @Override
            public void onFailure() {

            }
        };
        Authentication.getCurrentUser(getContext(), callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_landing_page, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        login = view.findViewById(R.id.btn_login_landing_page);
        signup = view.findViewById(R.id.btn_signup_landing_page);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(LandingPageFragmentDirections.actionLandingPageFragmentToLoginFragment());
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(LandingPageFragmentDirections.actionLandingPageFragmentToSignupFragment());
            }
        });
    }
}