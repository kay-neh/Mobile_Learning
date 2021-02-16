package com.example.mobilelearning.utils;

public class Books implements java.io.Serializable {
    private String bookId,bookName,bookUploadDate,bookSize,bookDownloadUrl;

    public Books() {
    }

    public Books(String bookId, String bookName, String bookUploadDate, String bookSize) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookUploadDate = bookUploadDate;
        this.bookSize = bookSize;
    }

    public Books(String bookId, String bookName, String bookUploadDate, String bookSize, String bookDownloadUrl) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookUploadDate = bookUploadDate;
        this.bookSize = bookSize;
        this.bookDownloadUrl = bookDownloadUrl;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookUploadDate() {
        return bookUploadDate;
    }

    public void setBookUploadDate(String bookUploadDate) {
        this.bookUploadDate = bookUploadDate;
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
