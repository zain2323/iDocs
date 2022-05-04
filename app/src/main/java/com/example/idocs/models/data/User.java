package com.example.idocs.models.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

@Entity(tableName = "user")
public class User {

    @SerializedName("$id")
    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private int registration;
    private boolean status;
    private int passwordUpdate;
    private String email;
    private boolean emailVerification;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRegistration() {
        return registration;
    }

    public boolean isStatus() {
        return status;
    }

    public int getPasswordUpdate() {
        return passwordUpdate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRegistration(int registration) {
        this.registration = registration;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setPasswordUpdate(int passwordUpdate) {
        this.passwordUpdate = passwordUpdate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEmailVerification(boolean emailVerification) {
        this.emailVerification = emailVerification;
    }

    public String getEmail() {
        return email;
    }

    public boolean isEmailVerification() {
        return emailVerification;
    }

}
