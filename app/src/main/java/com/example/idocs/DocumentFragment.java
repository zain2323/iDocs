package com.example.idocs;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anggrayudi.storage.SimpleStorageHelper;
import com.anggrayudi.storage.file.DocumentFileUtils;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class DocumentFragment extends Fragment implements GroupDocumentsAdapter.OnItemClickListener {
    private RecyclerView documentRecyclerView;
    SimpleStorageHelper simpleStorageHelper;
    private WorkspaceViewModel workspaceViewModel;
    private View rootView;
    private int groupId;
    private int workspaceId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(rootView).navigate(DocumentFragmentDirections.actionDocumentFragmentToGroupFragment(workspaceId));
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.rootView = inflater.inflate(R.layout.fragment_document, container, false);
        return this.rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert getArguments() != null;
        this.groupId = getArguments().getInt("GROUP_ID");
        this.workspaceId = getArguments().getInt("WORKSPACE_ID");
        GroupDocumentsAdapter documentAdapter = new GroupDocumentsAdapter(this, getContext());
        workspaceViewModel = new ViewModelProvider(this).get(WorkspaceViewModel.class);
        workspaceViewModel.getGroupWithDocuments(groupId).observe((LifecycleOwner) getContext(), new Observer<List<GroupWithDocuments>>() {
            @Override
            public void onChanged(List<GroupWithDocuments> groupWithDocuments) {
                documentAdapter.submitList(groupWithDocuments.get(0).getDocuments());
            }
        });
        simpleStorageHelper = new SimpleStorageHelper(this);
        documentRecyclerView = view.findViewById(R.id.recyclerview_document);
        documentRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        documentRecyclerView.setAdapter(documentAdapter);
        selectAndSaveDocs(savedInstanceState);
    }

    @Override
    public void onItemClick(Document document)
    {
        openDoc(document.getDocumentUri());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.add_document, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_add_document)
        {
            simpleStorageHelper.openFilePicker(3, true, "application/pdf");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void selectAndSaveDocs(Bundle savedState)
    {
        simpleStorageHelper.setOnFileSelected((requestCode, files) -> {
            for (int i = 0; i < files.size(); i++)
            {
                String name = DocumentFileUtils.getFullName(files.get(i));
                Utils utils = new Utils(getContext());
                try {
                    Uri savedUri = utils.savePDFToInternalStorageV2(getPdfBytes(files.get(0).getUri()), name, "docDir");
                    Document document = new Document(name, savedUri.toString(), this.groupId);
                    workspaceViewModel.insertDocument(document);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        });
    }
    private byte[] getPdfBytes(Uri uri)
    {
        try {
            ParcelFileDescriptor pdf = getContext().getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = pdf.getFileDescriptor();
            byte[] data = new byte[(int) pdf.getStatSize()];
            FileInputStream fileInputStream = new FileInputStream(fileDescriptor);
            fileInputStream.read(data);
            return data;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }


    private void openDoc(String fileUri) {
        Uri uri = Uri.parse(fileUri);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "application/pdf");
        Intent target = Intent.createChooser(intent, "Open File");
        try {
            startActivity(target);
        }
        catch (ActivityNotFoundException e) {
            Toast.makeText(getContext(), "Please install some pdf viewer application.", Toast.LENGTH_SHORT).show();
        }
    }
}