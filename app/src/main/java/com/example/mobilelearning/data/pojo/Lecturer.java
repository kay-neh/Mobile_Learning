package com.example.mobilelearning.data.pojo;

public class Lecturer {
    private String lecturerId,lecturerName,lecturerPhone,lecturerPhoto;

    public Lecturer() {
    }

    public Lecturer(String lecturerId, String lecturerName, String lecturerPhone) {
        this.lecturerId = lecturerId;
        this.lecturerName = lecturerName;
        this.lecturerPhone = lecturerPhone;
    }

    public Lecturer(String lecturerId, String lecturerName, String lecturerPhone, String lecturerPhoto) {
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
