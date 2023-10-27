package com.example.mobilelearning.data.pojo;

public class CourseOutline {
    private String courseOutlineTitle,courseOutlineDesc;

    public CourseOutline() {
    }

    public CourseOutline(String courseOutlineTitle, String courseOutlineDesc) {
        this.courseOutlineTitle = courseOutlineTitle;
        this.courseOutlineDesc = courseOutlineDesc;
    }

    public String getCourseOutlineTitle() {
        return courseOutlineTitle;
    }

    public void setCourseOutlineTitle(String courseOutlineTitle) {
        this.courseOutlineTitle = courseOutlineTitle;
    }

    public String getCourseOutlineDesc() {
        return courseOutlineDesc;
    }

    public void setCourseOutlineDesc(String courseOutlineDesc) {
        this.courseOutlineDesc = courseOutlineDesc;
    }
}
