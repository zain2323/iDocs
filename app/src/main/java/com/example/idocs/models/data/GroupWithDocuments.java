package com.example.idocs.models.data;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class GroupWithDocuments
{
    @Embedded private Group group;
    @Relation(
            parentColumn = "groupId",
            entityColumn = "groupId"
    )
    public List<Document> documents;

    public GroupWithDocuments(Group group, List<Document> documents) {
        this.group = group;
        this.documents = documents;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
}
