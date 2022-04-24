package com.example.idocs;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "GroupTable")
public class Group
{
    @PrimaryKey(autoGenerate = true)
    private long groupId;
    private String groupName;
    private int workspaceId;

    public Group(String groupName, int workspaceId) {
        this.groupName = groupName;
        this.workspaceId = workspaceId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(int workspaceId) {
        this.workspaceId = workspaceId;
    }
}
