package com.example.idocs.ui.views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.idocs.R;
import com.example.idocs.utils.Utils;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;

import java.util.ArrayList;


public class CreateNewWorkspaceFragment extends Fragment {
    private TextView create;
    private TextView back;
    private EditText workspaceName;
    private ImageView workspaceImg;
    private ArrayList<Uri> images;
    private String IMAGE_NAME = null;
    public String PATH = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_new_workspace, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        create = view.findViewById(R.id.bt_create_workspace);
        back = view.findViewById(R.id.bt_back_workspace);
        workspaceName = view.findViewById(R.id.et_workspace_name);
        workspaceImg = view.findViewById(R.id.img_workspace);

        create.setEnabled(false);
        create.setTextColor(Color.GRAY);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    create.setEnabled(false);
                    create.setTextColor(Color.GRAY);
                } else {
                    create.setEnabled(true);
                    create.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        workspaceName.addTextChangedListener(textWatcher);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = workspaceName.getText().toString();
                Navigation.findNavController(view).navigate(CreateNewWorkspaceFragmentDirections.actionCreateNewWorkspaceFragmentToWorkspaceFragment(name, PATH, IMAGE_NAME));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(CreateNewWorkspaceFragmentDirections.actionCreateNewWorkspaceFragmentToWorkspaceFragment(null, null, null));
            }
        });

        workspaceImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });
    }

    private void openImagePicker() {
        FishBun.with(this)
                .setImageAdapter(new GlideAdapter())
                .setMinCount(1)
                .setMaxCount(1)
                .startAlbum();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageData) {
        super.onActivityResult(requestCode, resultCode, imageData);
        if (requestCode == FishBun.FISHBUN_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            images = imageData.getParcelableArrayListExtra(FishBun.INTENT_PATH);
            if (images != null) {
                if (images.get(0) != null) {
                    workspaceImg.setImageURI(images.get(0));
                    Log.i("PATH", images.get(0).getPath());
                    Drawable imageDrawable = workspaceImg.getDrawable();
                    Bitmap bitmap = ((BitmapDrawable) imageDrawable).getBitmap();

                    Utils utils = new Utils(getContext());
                    String filename = utils.getFileName(bitmap);
                    String path = utils.saveToInternalStorage(bitmap, filename, ".jpg", "imgDir");
                    PATH = path;
                    IMAGE_NAME = filename;
                }
            }
        }
    }
}