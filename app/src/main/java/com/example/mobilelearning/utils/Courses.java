package com.example.mobilelearning.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Courses {

    String courseCode;
    String courseTitle;
    String courseImg;

    String courseDescription;
    String courseOutlineTitle;
    String courseOutlineDesc;
    String courseOutlineIndex;

    String[]books;

    String[] courseLecturer;

    String [] courseOut1;
    String [] courseOut2;
    String [] courseOut3;


    public Courses(){
    }

         public Courses(String courseCode, String courseImg, String courseTitle,String[] courseLecturer,String courseDescription,String[] books,String [] courseOut1,String [] courseOut2,String [] courseOut3){
            this.courseCode = courseCode;
            this.courseTitle = courseTitle;
            this.courseImg = courseImg;
            this.courseLecturer = courseLecturer;
            this.courseDescription = courseDescription;
            this.books = books;
            this.courseOut1 = courseOut1;
            this.courseOut2 = courseOut2;
            this.courseOut3 = courseOut3;
        }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getCourseImg() { return courseImg; }

    public String getCourseDescription() {
        return courseDescription;
    }

    public String[] getCourseLecturer() {
        return courseLecturer;
    }

    public String[] getBooks() {
        return books;
    }

    public String[] getCourseOut1() {
        return courseOut1;
    }

    public String[] getCourseOut2() {
        return courseOut2;
    }

    public String[] getCourseOut3() {
        return courseOut3;
    }
}
