package com.example.idocs.models.data;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class WorkspaceWithGroup
{
    @Embedded private Workspace workspace;
    @Relation(
            parentColumn = "id",
            entityColumn = "workspaceId"
    )
    public List<Group> groups;

    public WorkspaceWithGroup(Workspace workspace, List<Group> groups) {
        this.workspace = workspace;
        this.groups = groups;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
