package com.example.idocs.models.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "workspace")
public class Workspace
{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int noOfGroups;
    private String name;
    private String image;
    private String path = null;
    private int imageResourceId;

    public Workspace(String name, String image)
    {
        this.name = name;
        this.noOfGroups = 0;
        this.image = image;

        this.imageResourceId = -1;
    }

    @Ignore
    public Workspace(String name, int imageResourceId)
    {
        this.name = name;
        this.noOfGroups = 0;
        this.imageResourceId = imageResourceId;
        this.image = " ";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getNoOfGroups() {
        return noOfGroups;
    }

    public void setNoOfGroups(int noOfGroups) {
        this.noOfGroups = noOfGroups;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
