package com.example.idocs.ui.views;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anggrayudi.storage.SimpleStorageHelper;
import com.anggrayudi.storage.file.DocumentFileUtils;
import com.example.idocs.R;
import com.example.idocs.ui.adapters.DocumentAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CreateNewGroupFragment extends Fragment implements DocumentAdapter.OnItemClickListener{
    private View root;
    private SimpleStorageHelper simpleStorageHelper;
    private FloatingActionButton btnAddDocs;
    private Button create;
    private Button back;
    private EditText groupName;
    private RecyclerView documentRecyclerView;
    private DocumentAdapter adapter;
    private ArrayList<String> documentsName;
    private ArrayList<String> documentsUri;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.root = inflater.inflate(R.layout.fragment_create_new_group, container, false);
        return this.root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        documentRecyclerView = view.findViewById(R.id.recyclerview_document);
        create = view.findViewById(R.id.bt_create_group);
        back = view.findViewById(R.id.bt_back_group);
        groupName = view.findViewById(R.id.et_group_name);
        btnAddDocs = view.findViewById(R.id.btn_add_docs);

        simpleStorageHelper = new SimpleStorageHelper(this);

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
        groupName.addTextChangedListener(textWatcher);

        btnAddDocs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleStorageHelper.openFilePicker(3, true, "application/pdf");
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getArguments() != null;
                int workspace_id = getArguments().getInt("WORKSPACE_ID");
                String name = groupName.getText().toString();
                String[] docNameArr = null;
                String[] docUriArr = null;
                if (documentsName != null && documentsUri != null)
                {
                    docNameArr = documentsName.toArray(new String[documentsName.size()]);
                    docUriArr = documentsUri.toArray(new String[documentsUri.size()]);
                }
                Navigation.findNavController(view).navigate(CreateNewGroupFragmentDirections.actionCreateNewGroupFragmentToGroupFragment(docNameArr, docUriArr, name, workspace_id));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getArguments() != null;
                int workspace_id = getArguments().getInt("WORKSPACE_ID");
                Navigation.findNavController(view).navigate(CreateNewGroupFragmentDirections.actionCreateNewGroupFragmentToGroupFragment(null, null, null, workspace_id));
            }
        });
        getFileNamesAndPopulate(savedInstanceState);
    }

    private void getFileNamesAndPopulate(Bundle savedState)
    {
        simpleStorageHelper.setOnFileSelected((requestCode, files) -> {
            this.documentsName = new ArrayList<>();
            this.documentsUri = new ArrayList<>();
            for (int i = 0; i < files.size(); i++)
            {
                String name = DocumentFileUtils.getFullName(files.get(i));
                String uri = files.get(i).getUri().toString();
                this.documentsName.add(name);
                this.documentsUri.add(uri);
            }

            adapter = new DocumentAdapter(documentsName, this);
            documentRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3, RecyclerView.VERTICAL, false));
            documentRecyclerView.setAdapter(adapter);
            return null;
        });
    }

    @Override
    public void onItemClick(int pos)
    {
        documentsName.remove(pos);
        adapter.notifyItemRemoved(pos);
    }
}