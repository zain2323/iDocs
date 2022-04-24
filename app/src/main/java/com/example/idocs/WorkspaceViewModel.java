package com.example.idocs;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class WorkspaceViewModel extends AndroidViewModel
{
    private WorkspaceRepository workspaceRepository;
    private LiveData<List<Workspace>> allWorkspaces;

    public WorkspaceViewModel(@NonNull Application application)
    {
        super(application);
        workspaceRepository = new WorkspaceRepository(application);
        allWorkspaces = workspaceRepository.getAllWorkspaces();
    }

    public void insertWorkspace(Workspace workspace)
    {
        workspaceRepository.insertWorkspace(workspace);
    }

    public void deleteWorkspace(Workspace workspace)
    {
        workspaceRepository.deleteWorkspace(workspace);
    }

    public void updateWorkspace(Workspace workspace)
    {
        workspaceRepository.updateWorkspace(workspace);
    }

    public void deleteAllWorkspaces()
    {
        workspaceRepository.deleteAllWorkspaces();
    }

    public LiveData<List<Workspace>> getAllWorkspaces()
    {
        return allWorkspaces;
    }

    public long insertGroup(Group group) throws ExecutionException, InterruptedException {
        long id = workspaceRepository.insertGroup(group);
        return id;
    }

    public void deleteGroup(Group group)
    {
        workspaceRepository.deleteGroup(group);
    }

    public void updateGroup(Group group)
    {
        workspaceRepository.updateGroup(group);
    }

    public LiveData<List<WorkspaceWithGroup>> getWorkspaceWithGroups(int workspaceId)
    {
        return workspaceRepository.getWorkspaceWithGroups(workspaceId);
    }

    public void insertDocument(Document document)
    {
        workspaceRepository.insertDocument(document);
    }

    public void deleteDocument(Document document)
    {
        workspaceRepository.deleteDocument(document);
    }

    public void updateDocument(Document document)
    {
        workspaceRepository.updateDocument(document);
    }

    public LiveData<List<GroupWithDocuments>> getGroupWithDocuments(int groupId)
    {
        return workspaceRepository.getGroupWithDocuments(groupId);
    }

}
