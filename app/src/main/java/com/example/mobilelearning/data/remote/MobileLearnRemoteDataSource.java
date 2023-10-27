package com.example.mobilelearning.data.remote;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mobilelearning.data.BaseDataSource;
import com.example.mobilelearning.data.pojo.Book;
import com.example.mobilelearning.data.pojo.Course;
import com.example.mobilelearning.data.pojo.CourseOutline;
import com.example.mobilelearning.data.pojo.Lecturer;
import com.example.mobilelearning.data.pojo.User;
import com.example.mobilelearning.ui.coursedetails.books.BooksAdapter;
import com.example.mobilelearning.utils.Constants;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class MobileLearnRemoteDataSource implements BaseDataSource {

    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;

    public MobileLearnRemoteDataSource(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
    }

    @Override
    public void saveUser(final User user, String userId) {
        final DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.KEY_USERS + "/" +userId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.hasChildren()){
                    databaseReference.setValue(user);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Override
    public LiveData<User> getUser(String userId) {
        DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.KEY_USERS + "/" +userId);
        final MutableLiveData<User> data = new MutableLiveData<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                data.setValue(user);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return data;
    }

    @Override
    public void updateUserPhoto(final String userId, Uri uri, final String currentPhoto) {
        StorageReference storageReference = firebaseStorage.getReference(Constants.PATH_PROFILE_PHOTO);
        final StorageReference profilePhotoRef = storageReference.child(userId + "/" + uri.getLastPathSegment());

        profilePhotoRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                profilePhotoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        DatabaseReference photoRef = firebaseDatabase.getReference(Constants.KEY_USERS + "/" +userId + "/" +Constants.KEY_USER_PHOTO);
                        if(currentPhoto != null){
                            deleteUserPhoto(currentPhoto);
                        }
                        photoRef.setValue(uri.toString());
                    }
                });
            }
        });

    }

    private void deleteUserPhoto(String userPhoto) {
        StorageReference ref = firebaseStorage.getReferenceFromUrl(userPhoto);
        ref.delete();
    }

    @Override
    public LiveData<List<Course>> getCourses() {
        final DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.KEY_COURSES);
        final MutableLiveData<List<Course>> data = new MutableLiveData<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Course> course  = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Course c = dataSnapshot1.getValue(Course.class);
                    course.add(c);
                }
                data.setValue(course);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return data;
    }

    @Override
    public LiveData<List<Course>> getUserCourses(String userId) {
        DatabaseReference courseRef = firebaseDatabase.getReference(Constants.KEY_COURSES);
        final MutableLiveData<List<Course>> data = new MutableLiveData<>();
        courseRef.orderByChild(Constants.KEY_USERS + "/" +userId).equalTo(true).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Course> myCourse = new ArrayList<>();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Course course = dataSnapshot1.getValue(Course.class);
                        myCourse.add(course);
                    }
                data.setValue(myCourse);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return data;
    }

    @Override
    public LiveData<Course> getCourse(String courseId) {
        DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.KEY_COURSES + "/" +courseId);
        final MutableLiveData<Course> data = new MutableLiveData<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Course h = dataSnapshot.getValue(Course.class);
                data.setValue(h);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return data;
    }

    @Override
    public LiveData<List<Lecturer>> getLecturers(String courseId) {
        DatabaseReference lecturersRef = firebaseDatabase.getReference(Constants.KEY_LECTURERS);
        final MutableLiveData<List<Lecturer>> data = new MutableLiveData<>();
        lecturersRef.orderByChild(Constants.KEY_COURSES + "/" +courseId).equalTo(true).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    List<Lecturer> lecturers = new ArrayList<>();
                    for (DataSnapshot d : snapshot.getChildren()) {
                        Lecturer l = d.getValue(Lecturer.class);
                        lecturers.add(l);
                    }
                    data.setValue(lecturers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return data;
    }

    @Override
    public void registerCourse(String userId, String courseId) {
        DatabaseReference ref2 = firebaseDatabase.getReference(Constants.KEY_USERS +"/" +userId +"/" +Constants.KEY_COURSES);
        DatabaseReference ref3 = firebaseDatabase.getReference(Constants.KEY_COURSES +"/" +courseId +"/" +Constants.KEY_USERS);
        ref2.child(courseId).setValue(true);
        ref3.child(userId).setValue(true);
    }

    @Override
    public void unregisterCourse(String userId, String courseId) {
        DatabaseReference ref2 = firebaseDatabase.getReference(Constants.KEY_USERS +"/" +userId +"/" +Constants.KEY_COURSES);
        DatabaseReference ref3 = firebaseDatabase.getReference(Constants.KEY_COURSES +"/" +courseId +"/" +Constants.KEY_USERS);
        ref2.child(courseId).setValue(null);
        ref3.child(userId).setValue(null);
    }

    @Override
    public LiveData<Boolean> isRegistered(String userId, final String courseId) {
        DatabaseReference ref2 = firebaseDatabase.getReference(Constants.KEY_USERS +"/" +userId);
        final MutableLiveData<Boolean> data = new MutableLiveData<>();
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(Constants.KEY_COURSES +"/" +courseId)) {
                    data.setValue(true);
                } else {
                    data.setValue(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return data;
    }

    @Override
    public LiveData<List<CourseOutline>> getCourseOutlines(String courseId) {
        DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.KEY_COURSES +"/" +courseId +"/" +Constants.KEY_COURSE_OUTLINE);
        final MutableLiveData<List<CourseOutline>> data = new MutableLiveData<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<CourseOutline> courseOutlines = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    CourseOutline courseOutline = dataSnapshot1.getValue(CourseOutline.class);
                    courseOutlines.add(courseOutline);
                }
                data.setValue(courseOutlines);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return data;
    }

    @Override
    public LiveData<List<Book>> getBooks(String courseId) {
        DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.KEY_BOOKS);
        final MutableLiveData<List<Book>> data = new MutableLiveData<>();
        databaseReference.orderByChild("courses/" + courseId).equalTo(true).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<Book> books = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Book book = dataSnapshot1.getValue(Book.class);
                    books.add(book);
                }
                data.setValue(books);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return data;
    }

    @Override
    public LiveData<String> getBookDownloadUrl(Book book) {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReferenceFromUrl(book.getBookDownloadUrl());
        final MutableLiveData<String> data = new MutableLiveData<>();
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String downloadUrl = uri.toString();
                data.setValue(downloadUrl);
            }
        });
        return data;
    }
}
