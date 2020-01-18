package com.example.mobilelearning.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Courses {

     String courseCode,courseTitle,courseImg,courseDescription;
   // private Map<String, Books> books = new HashMap<>();
   // private Map<String, CourseOutline> courseOutline = new HashMap<>();
  //  List<String> courseLecturer = new ArrayList<>();
    //List<Books> books = new ArrayList<>();
    //List<CourseOutline> courseOutline = new ArrayList<>();

    public Courses(){
    }

    public Courses(String courseCode, String courseTitle, String courseImg) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.courseImg = courseImg;
    }

    public Courses(String courseCode, String courseTitle, String courseImg, String courseDescription) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.courseImg = courseImg;
        this.courseDescription = courseDescription;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseImg() {
        return courseImg;
    }

    public void setCourseImg(String courseImg) {
        this.courseImg = courseImg;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }
}
