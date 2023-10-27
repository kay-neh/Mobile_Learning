package com.example.mobilelearning.data.pojo;

import java.util.List;

public class Course {
    private String courseId,courseName,courseCode,courseLevel,courseSemester,courseDescription, coursePhoto;
    private List<CourseOutline> courseOutline;

    public Course(){
    }

    public Course(String courseId, String courseName, String courseCode, String courseLevel, String courseSemester, String courseDescription, List<CourseOutline> courseOutline) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.courseLevel = courseLevel;
        this.courseSemester = courseSemester;
        this.courseDescription = courseDescription;
        this.courseOutline = courseOutline;
    }

    public Course(String courseId, String courseName, String courseCode, String courseLevel, String courseSemester, String courseDescription, String coursePhoto, List<CourseOutline> courseOutline) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.courseLevel = courseLevel;
        this.courseSemester = courseSemester;
        this.courseDescription = courseDescription;
        this.coursePhoto = coursePhoto;
        this.courseOutline = courseOutline;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseLevel() {
        return courseLevel;
    }

    public void setCourseLevel(String courseLevel) {
        this.courseLevel = courseLevel;
    }

    public String getCourseSemester() {
        return courseSemester;
    }

    public void setCourseSemester(String courseSemester) {
        this.courseSemester = courseSemester;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCoursePhoto() {
        return coursePhoto;
    }

    public void setCoursePhoto(String coursePhoto) {
        this.coursePhoto = coursePhoto;
    }

    public List<CourseOutline> getCourseOutline() {
        return courseOutline;
    }

    public void setCourseOutline(List<CourseOutline> courseOutline) {
        this.courseOutline = courseOutline;
    }
}
