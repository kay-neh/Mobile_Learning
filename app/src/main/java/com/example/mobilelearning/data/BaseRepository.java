package com.example.mobilelearning.data;

import android.net.Uri;

import androidx.lifecycle.LiveData;

import com.example.mobilelearning.data.pojo.Book;
import com.example.mobilelearning.data.pojo.Course;
import com.example.mobilelearning.data.pojo.CourseOutline;
import com.example.mobilelearning.data.pojo.Lecturer;
import com.example.mobilelearning.data.pojo.User;
import com.example.mobilelearning.data.remote.MobileLearnRemoteDataSource;

import java.util.List;

public class BaseRepository {

    private final MobileLearnRemoteDataSource remoteDataSource;

    public BaseRepository(){
        remoteDataSource = new MobileLearnRemoteDataSource();
    }

    public void saveUser(User user, String userId){
        remoteDataSource.saveUser(user,userId);
    }

    public LiveData<User> getUser(String userId){
        return remoteDataSource.getUser(userId);
    }

    public void updateProfilePhoto(String userId, Uri uri, String currentPhoto){
        remoteDataSource.updateUserPhoto(userId, uri, currentPhoto);
    }

    public LiveData<List<Course>> getCourses(){
        return remoteDataSource.getCourses();
    }

    public LiveData<List<Course>> getUserCourses(String userId){
        return remoteDataSource.getUserCourses(userId);
    }

    public LiveData<Course> getCourse(String courseId){
        return remoteDataSource.getCourse(courseId);
    }

    public LiveData<List<Lecturer>> getLecturers(String courseId){
        return remoteDataSource.getLecturers(courseId);
    }

    public void registerCourse(String userId, String courseId) {
        remoteDataSource.registerCourse(userId, courseId);
    }

    public void unregisterCourse(String userId, String courseId) {
        remoteDataSource.unregisterCourse(userId, courseId);
    }

    public LiveData<Boolean> isRegistered(String userId, final String courseId) {
        return remoteDataSource.isRegistered(userId, courseId);
    }

    public LiveData<List<CourseOutline>> getCourseOutlines(String courseId) {
        return remoteDataSource.getCourseOutlines(courseId);
    }

    public LiveData<List<Book>> getBooks(String courseId) {
        return remoteDataSource.getBooks(courseId);
    }

    public LiveData<String> getBookDownloadUrl(Book book){
        return remoteDataSource.getBookDownloadUrl(book);
    }

}
