package com.example.idocs.ui.views;

import static com.example.idocs.ui.views.LoginFragment.read_session_id;
import static com.example.idocs.ui.views.LoginFragment.session_id;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.idocs.R;

public class LandingPageFragment extends Fragment {

    private Button login;
    private Button signup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_landing_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Log.i("INFO", read_session_id());
//        if (read_session_id() != null) {
//            if (!read_session_id().equals("")) {
//                Navigation.findNavController(view).navigate(LandingPageFragmentDirections.actionLandingPageFragmentToWorkspaceFragment());
//            }
//        }
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