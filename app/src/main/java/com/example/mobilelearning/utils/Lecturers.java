package com.example.mobilelearning.utils;

public class Lecturers {
    private String lecturerId,lecturerName,lecturerPhone,lecturerPhoto;

    public Lecturers() {
    }

    public Lecturers(String lecturerId, String lecturerName, String lecturerPhone) {
        this.lecturerId = lecturerId;
        this.lecturerName = lecturerName;
        this.lecturerPhone = lecturerPhone;
    }

    public Lecturers(String lecturerId, String lecturerName, String lecturerPhone, String lecturerPhoto) {
        this.lecturerId = lecturerId;
        this.lecturerName = lecturerName;
        this.lecturerPhone = lecturerPhone;
        this.lecturerPhoto = lecturerPhoto;
    }

    public String getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(String lecturerId) {
        this.lecturerId = lecturerId;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public String getLecturerPhone() {
        return lecturerPhone;
    }

    public void setLecturerPhone(String lecturerPhone) {
        this.lecturerPhone = lecturerPhone;
    }

    public String getLecturerPhoto() {
        return lecturerPhoto;
    }

    public void setLecturerPhoto(String lecturerPhoto) {
        this.lecturerPhoto = lecturerPhoto;
    }
}
