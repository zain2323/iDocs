package com.example.idocs.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.idocs.models.data.Document;
import com.example.idocs.models.data.Group;
import com.example.idocs.models.data.GroupWithDocuments;
import com.example.idocs.models.data.Workspace;
import com.example.idocs.models.AppRepository;
import com.example.idocs.models.data.WorkspaceWithGroup;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class AppViewModel extends AndroidViewModel
{
    private AppRepository appRepository;
    private LiveData<List<Workspace>> allWorkspaces;

    public AppViewModel(@NonNull Application application)
    {
        super(application);
        appRepository = new AppRepository(application);
        allWorkspaces = appRepository.getAllWorkspaces();
    }

    public void insertWorkspace(Workspace workspace)
    {
        appRepository.insertWorkspace(workspace);
    }

    public void deleteWorkspace(Workspace workspace)
    {
        appRepository.deleteWorkspace(workspace);
    }

    public void updateWorkspace(Workspace workspace)
    {
        appRepository.updateWorkspace(workspace);
    }

    public void deleteAllWorkspaces()
    {
        appRepository.deleteAllWorkspaces();
    }

    public LiveData<List<Workspace>> getAllWorkspaces()
    {
        return allWorkspaces;
    }

    public long insertGroup(Group group) throws ExecutionException, InterruptedException {
        long id = appRepository.insertGroup(group);
        return id;
    }

    public void deleteGroup(Group group)
    {
        appRepository.deleteGroup(group);
    }

    public void updateGroup(Group group)
    {
        appRepository.updateGroup(group);
    }

    public LiveData<List<WorkspaceWithGroup>> getWorkspaceWithGroups(int workspaceId)
    {
        return appRepository.getWorkspaceWithGroups(workspaceId);
    }

    public void insertDocument(Document document)
    {
        appRepository.insertDocument(document);
    }

    public void deleteDocument(Document document)
    {
        appRepository.deleteDocument(document);
    }

    public void updateDocument(Document document)
    {
        appRepository.updateDocument(document);
    }

    public LiveData<List<GroupWithDocuments>> getGroupWithDocuments(int groupId)
    {
        return appRepository.getGroupWithDocuments(groupId);
    }

}
