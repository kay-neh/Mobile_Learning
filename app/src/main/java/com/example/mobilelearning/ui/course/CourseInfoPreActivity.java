package com.example.mobilelearning.ui.course;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mobilelearning.R;
import com.example.mobilelearning.data.BaseRepository;
import com.example.mobilelearning.data.pojo.Course;
import com.example.mobilelearning.utils.Constants;
import com.example.mobilelearning.utils.LecturerAdapter;
import com.example.mobilelearning.data.pojo.Lecturer;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class CourseInfoPreActivity extends AppCompatActivity {

    String courseId,courseName;
    ImageView courseImage;
    TextView courseTitle,courseCode, courseDescription;
    ExtendedFloatingActionButton addCourse,removeCourse;
    RecyclerView lectRecyclerView;
    LecturerAdapter lecturerAdapter;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_info_pre);

        firebaseAuth = FirebaseAuth.getInstance();
        courseImage = findViewById(R.id.course_image);
        courseTitle = findViewById(R.id.course_title);
        courseCode = findViewById(R.id.course_code);
        courseDescription = findViewById(R.id.course_description);
        lectRecyclerView = findViewById(R.id.lect_recycler_view);


        //Toolbar
        Toolbar tool = findViewById(R.id.toolbar_course_pre_sub);
        tool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        //Get Data from main activity for courseinfo post reg
        Intent i = getIntent();
        courseId = i.getStringExtra(Constants.KEY_COURSE_ID);

        initAdapter();

        // Access Repository
        final BaseRepository baseRepository = new BaseRepository();

        baseRepository.getCourse(courseId).observe(this, new Observer<Course>() {
            @Override
            public void onChanged(Course course) {
                if (course != null){
                    //Populating the activity
                    courseName = course.getCourseName();
                    courseTitle.setText(course.getCourseName());
                    courseCode.setText(course.getCourseCode());
                    courseDescription.setText(course.getCourseDescription());
                    Glide.with(getApplicationContext()).load(course.getCoursePhoto()).into(courseImage);
                }
            }
        });

        baseRepository.getLecturers(courseId).observe(this, new Observer<List<Lecturer>>() {
            @Override
            public void onChanged(List<Lecturer> lecturers) {
                if(lecturers != null){
                    lecturerAdapter.setList(lecturers);
                }
            }
        });


        //Floating Action Button
        addCourse = findViewById(R.id.add_course);
        removeCourse = findViewById(R.id.remove_course);

        //Switches the FAB between register and unregister based on users courseList
        if(firebaseAuth.getCurrentUser()!=null) {
            baseRepository.isRegistered(firebaseAuth.getCurrentUser().getUid(),courseId).observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {
                    if(aBoolean){
                        addCourse.setVisibility(View.GONE);
                        removeCourse.setVisibility(View.VISIBLE);
                    }else {
                        addCourse.setVisibility(View.VISIBLE);
                        removeCourse.setVisibility(View.GONE);
                    }
                }
            });
        }

        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseRepository.registerCourse(firebaseAuth.getCurrentUser().getUid(),courseId);
                Toast.makeText(CourseInfoPreActivity.this,""+courseName+" added to my courses",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        removeCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseRepository.unregisterCourse(firebaseAuth.getCurrentUser().getUid(),courseId);
                Toast.makeText(CourseInfoPreActivity.this,""+courseName+" removed from my courses",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    public void initAdapter(){
        LinearLayoutManager llm = new LinearLayoutManager(this);
        lectRecyclerView.setLayoutManager(llm);
        lectRecyclerView.setHasFixedSize(true);
        lecturerAdapter = new LecturerAdapter();
        lectRecyclerView.setAdapter(lecturerAdapter);
    }

}