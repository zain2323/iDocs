package com.example.idocs;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GroupFragment extends Fragment implements GroupAdapter.OnItemClickListener {
    private RecyclerView groupRecyclerView;
    private WorkspaceViewModel workspaceViewModel;
    private View rootView;
    private FloatingActionButton btnAddGroup;
    private int workspaceId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.rootView = inflater.inflate(R.layout.fragment_group, container, false);
        return this.rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null)
            this.workspaceId = getArguments().getInt("workspace_id");
        else
        {
            Navigation.findNavController(view).navigate(GroupFragmentDirections.actionGroupFragmentToWorkspaceFragment());
        }

        GroupAdapter groupAdapter = new GroupAdapter(this, getContext());
        workspaceViewModel = new ViewModelProvider(this).get(WorkspaceViewModel.class);
        workspaceViewModel.getWorkspaceWithGroups(workspaceId).observe((LifecycleOwner) getContext(), new Observer<List<WorkspaceWithGroup>>() {
            @Override
            public void onChanged(List<WorkspaceWithGroup> workspaceWithGroups) {
                groupAdapter.submitList(workspaceWithGroups.get(0).getGroups());
            }
        });
        groupRecyclerView = view.findViewById(R.id.group_recyclerview);
        btnAddGroup = view.findViewById(R.id.btn_add_group);
        groupRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        groupRecyclerView.setAdapter(groupAdapter);

        btnAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(GroupFragmentDirections.actionGroupFragmentToCreateNewGroupFragment(workspaceId));
            }
        });

        if (getArguments() != null) {
            String name = getArguments().getString("GROUP_NAME");
            String[] documentsName = getArguments().getStringArray("DOCUMENTS_NAME");
            String[] documentsUri = getArguments().getStringArray("DOCUMENTS_URI");
            setArguments(null);
            if (name != null) {
                long groupId = 0;
                try {
                    groupId = createNewGroup(name);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (documentsName != null && documentsUri != null) {
                    try {
                        createDocuments(documentsName, documentsUri, groupId);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Toast.makeText(getContext(), "Group created" + groupId, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private long createNewGroup(String name) throws ExecutionException, InterruptedException {
        Group group = new Group(name, this.workspaceId);
        long id = workspaceViewModel.insertGroup(group);
        return id;
    }

    private void createDocuments(String[] documentsName, String[] documentsUri, long groupId) throws IOException {
        Utils utils = new Utils(getContext());
        for (int i = 0; i < documentsName.length; i++) {
            Uri uri = utils.savePDFToInternalStorageV2(utils.getPdfBytes(Uri.parse(documentsUri[i])), documentsName[0], "docDir");
            Document document = new Document(documentsName[i], uri.toString(), (int)groupId);
            workspaceViewModel.insertDocument(document);
            Log.i("Executed", "inserted");
        }
    }

    @Override
    public void onItemClick(Group group) {
        Navigation.findNavController(this.rootView).navigate(GroupFragmentDirections.actionGroupFragmentToDocumentFragment((int)group.getGroupId(), workspaceId));
    }

}