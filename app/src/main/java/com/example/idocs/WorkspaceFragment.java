package com.example.idocs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WorkspaceFragment extends Fragment implements WorkspaceAdapter.OnItemClickListener {
    private RecyclerView workspaceRecyclerView;
    private WorkspaceViewModel workspaceViewModel;
    private TextView newWorkspace;
    private View rootView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.rootView = inflater.inflate(R.layout.fragment_workspace, container, false);
        return this.rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        WorkspaceAdapter workspaceAdapter = new WorkspaceAdapter(this, getContext());
        workspaceViewModel = new ViewModelProvider(this).get(WorkspaceViewModel.class);
        workspaceViewModel.getAllWorkspaces().observe((LifecycleOwner) getContext(), new Observer<List<Workspace>>() {
            @Override
            public void onChanged(List<Workspace> workspaces) {
                workspaceAdapter.submitList(workspaces);
            }
        });
        workspaceRecyclerView = root.findViewById(R.id.workspace_recycler_view);
        newWorkspace = root.findViewById(R.id.tv_new_workspace);
        workspaceRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        workspaceRecyclerView.setAdapter(workspaceAdapter);

        newWorkspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @NonNull NavDirections action = WorkspaceFragmentDirections
                        .actionWorkspaceFragmentToCreateNewWorkspaceFragment();
                Navigation.findNavController(root).navigate(action);
            }
        });

        if (getArguments() != null)
        {
            String name = getArguments().getString("name");
            String image = getArguments().getString("image_name");
            String path = getArguments().getString("path");
            setArguments(null);
            if (name != null)
                createNewWorkspace(name, image, path);
        }
    }


    private void createNewWorkspace(String name, String image, String path)
    {
        Workspace workspace;
        if (image != null && path != null)
        {
            workspace = new Workspace(name, image);
            workspace.setPath(path);
        }
        else
        {
            workspace = new Workspace(name, R.drawable.ic_workspace);
        }
        Log.i("WORKSPACE", String.valueOf(workspaceViewModel));
        workspaceViewModel.insertWorkspace(workspace);
        Toast.makeText(getContext(), "New workspace added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(Workspace workspace)
    {
        Navigation.findNavController(this.rootView).navigate(WorkspaceFragmentDirections.actionWorkspaceFragmentToGroupFragment(workspace.getId()));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.show_map, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_show_map)
        {
            Navigation.findNavController(rootView).navigate(WorkspaceFragmentDirections.actionWorkspaceFragmentToStationaryShopsFragment());
            return true;
        }
        else if (id == R.id.menu_exit)
        {
            getActivity().onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}