package com.example.idocs;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "document")
public class Document
{
    @PrimaryKey(autoGenerate = true)
    private int document_id;
    private String documentName;
    private String documentUri;
    private int groupId;

    public Document(String documentName, String documentUri, int groupId) {
        this.documentName = documentName;
        this.groupId = groupId;
        this.documentUri = documentUri;
    }

    public int getDocument_id() {
        return document_id;
    }

    public void setDocument_id(int document_id) {
        this.document_id = document_id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getDocumentUri() {
        return documentUri;
    }

    public void setDocumentUri(String documentUri) {
        this.documentUri = documentUri;
    }
}
