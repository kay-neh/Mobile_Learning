package com.example.mobilelearning.utils;

public class CourseOutline {
    private String content,title;
    private String index;

    public CourseOutline() {
    }

    public CourseOutline(String content, String index, String title) {
        this.content = content;
        this.index = index;
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
