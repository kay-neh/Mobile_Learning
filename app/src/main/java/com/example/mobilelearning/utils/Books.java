package com.example.mobilelearning.utils;

public class Books implements java.io.Serializable {
    String bookName;
    String bookSize;
    String bookDownloadUrl;

    public Books(){}

    public Books(String bookName, String bookSize, String bookDownloadUrl) {
        this.bookName = bookName;
        this.bookSize = bookSize;
        this.bookDownloadUrl = bookDownloadUrl;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookSize() {
        return bookSize;
    }

    public void setBookSize(String bookSize) {
        this.bookSize = bookSize;
    }

    public String getBookDownloadUrl() {
        return bookDownloadUrl;
    }

    public void setBookDownloadUrl(String bookDownloadUrl) {
        this.bookDownloadUrl = bookDownloadUrl;
    }
}
