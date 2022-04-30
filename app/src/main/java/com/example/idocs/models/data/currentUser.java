package com.example.idocs.models.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "current_user")
public class currentUser {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String session_id;
    private String name;
    private String email;
    private boolean isConfirmed;

    public currentUser(int id, String session_id, String name, String email, boolean isConfirmed) {
        this.id = id;
        this.session_id = session_id;
        this.name = name;
        this.email = email;
        this.isConfirmed = isConfirmed;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }
}
