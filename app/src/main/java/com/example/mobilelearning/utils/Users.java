package com.example.mobilelearning.utils;

public class Users {
    private String userName;
    private String userEmail;
    private String userPhoto;

    public Users() {
    }

    public Users(String userName, String userEmail) {
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public Users(String userName, String userEmail, String userPhoto) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhoto = userPhoto;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() { return userEmail; }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }
}
