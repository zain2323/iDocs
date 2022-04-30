package com.example.idocs.models;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.idocs.models.data.Document;
import com.example.idocs.models.data.Group;
import com.example.idocs.models.data.GroupWithDocuments;
import com.example.idocs.models.data.Workspace;
import com.example.idocs.models.data.WorkspaceWithGroup;
import com.example.idocs.models.data.currentUser;

import java.util.List;

@androidx.room.Dao
public interface AppDao
{
    @Insert
    void insertWorkspace(Workspace workspace);

    @Update
    void updateWorkspace(Workspace workspace);

    @Delete
    void deleteWorkspace(Workspace workspace);

    @Transaction
    @Query("SELECT * FROM workspace ORDER BY id DESC")
    LiveData<List<Workspace>> getAllWorkspaces();

    @Query("DELETE FROM workspace")
    void deleteAllWorkspaces();

    @Insert
    long insertGroup(Group group);

    @Update
    void updateGroup(Group group);

    @Delete
    void deleteGroup(Group group);

    @Transaction
    @Query("SELECT * FROM workspace WHERE id = :workspaceId ORDER BY id DESC")
    LiveData<List<WorkspaceWithGroup>> getWorkspaceWithGroups(int workspaceId);

    @Insert
    void insertDocument(Document document);

    @Update
    void updateDocument(Document document);

    @Delete
    void deleteDocument(Document document);

    @Transaction
    @Query("SELECT * FROM GroupTable WHERE groupId = :groupId ORDER BY groupId DESC")
    LiveData<List<GroupWithDocuments>> getGroupWithDocuments(int groupId);

    @Insert
    void createUser(currentUser user);

    @Update
    void updateUser(currentUser user);
}

