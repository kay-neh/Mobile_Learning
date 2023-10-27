package com.example.mobilelearning.data;

import android.net.Uri;

import androidx.lifecycle.LiveData;

import com.example.mobilelearning.data.pojo.Book;
import com.example.mobilelearning.data.pojo.CourseOutline;
import com.example.mobilelearning.data.pojo.User;
import com.example.mobilelearning.data.pojo.Course;
import com.example.mobilelearning.data.pojo.Lecturer;

import java.util.List;

public interface BaseDataSource {
    public void saveUser(User user, String userId);
    public LiveData<User> getUser(String userId);
    public void updateUserPhoto(String userId, Uri uri, String currentPhoto);
    public LiveData<List<Course>> getCourses();
    public LiveData<List<Course>> getUserCourses(String userId);
    public LiveData<Course> getCourse(String courseId);
    public LiveData<List<Lecturer>> getLecturers(String courseId);
    public void registerCourse(String userId, String courseId);
    public void unregisterCourse(String userId, String courseId);
    public LiveData<Boolean> isRegistered(String userId, String courseId);
    public LiveData<List<CourseOutline>> getCourseOutlines(String courseId);
    public LiveData<List<Book>> getBooks(String courseId);
    public LiveData<String> getBookDownloadUrl(Book book);
}
