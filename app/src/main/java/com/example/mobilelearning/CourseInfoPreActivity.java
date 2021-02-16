package com.example.mobilelearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mobilelearning.utils.Courses;
import com.example.mobilelearning.utils.LecturerAdapter;
import com.example.mobilelearning.utils.Lecturers;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CourseInfoPreActivity extends AppCompatActivity {

    String courseId,courseName;
    List<Lecturers> selectedLecturers;

    ImageView courseImage;
    TextView courseTitle,courseCode,courseDes;
    ExtendedFloatingActionButton addCourse,removeCourse;
    RecyclerView lectRecyclerView;
    LecturerAdapter lecturerAdapter;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_info_pre);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        courseImage = findViewById(R.id.course_image);
        courseTitle = findViewById(R.id.course_title);
        courseCode = findViewById(R.id.course_code);
        courseDes = findViewById(R.id.course_description);
        lectRecyclerView = findViewById(R.id.lect_recycler_view);

        selectedLecturers = new ArrayList<>();

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
        courseId = i.getStringExtra("Course Id");


        databaseReference = firebaseDatabase.getReference("courses/"+courseId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Courses h = dataSnapshot.getValue(Courses.class);
                if (h != null){
                    //Populating courseinfo pre reg
                    courseName = h.getCourseName();
                    courseTitle.setText(h.getCourseName());
                    courseCode.setText(h.getCourseCode());
                    courseDes.setText(h.getCourseDescription());

                    Glide.with(getApplicationContext()).load(h.getCoursePhoto()).into(courseImage);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        initAdap();
        setLecturerFromDb();

        //Floating Action Button
        addCourse = findViewById(R.id.add_course);
        removeCourse = findViewById(R.id.remove_course);

        //Switches the FAB between register and unregister based on users courselist
        if(firebaseAuth.getCurrentUser()!=null) {
            DatabaseReference ref2 = firebaseDatabase.getReference("users/" + firebaseAuth.getCurrentUser().getUid());
            ref2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild("courses/" + courseId)) {
                        addCourse.setVisibility(View.GONE);
                        removeCourse.setVisibility(View.VISIBLE);
                    } else {
                        addCourse.setVisibility(View.VISIBLE);
                        removeCourse.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }

        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref2 = firebaseDatabase.getReference("users/"+firebaseAuth.getCurrentUser().getUid()+"/courses");
                DatabaseReference ref3 = firebaseDatabase.getReference("courses/"+courseId+"/users");
                ref2.child(courseId).setValue(true);
                ref3.child(firebaseAuth.getCurrentUser().getUid()).setValue(true);
                Toast.makeText(CourseInfoPreActivity.this,""+courseName+" added to my courses",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        removeCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref2 = firebaseDatabase.getReference("users/"+firebaseAuth.getCurrentUser().getUid()+"/courses");
                DatabaseReference ref3 = firebaseDatabase.getReference("courses/"+courseId+"/users");
                ref2.child(courseId).setValue(null);
                ref3.child(firebaseAuth.getCurrentUser().getUid()).setValue(null);
                Toast.makeText(CourseInfoPreActivity.this,""+courseName+" removed from my courses",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    public void initAdap(){
        LinearLayoutManager llm = new LinearLayoutManager(this);
        lectRecyclerView.setLayoutManager(llm);
        lectRecyclerView.setHasFixedSize(true);
        lecturerAdapter = new LecturerAdapter(this);
        lectRecyclerView.setAdapter(lecturerAdapter);
    }

    public void setLecturerFromDb(){
        DatabaseReference lecturersRef = firebaseDatabase.getReference("lecturers");
        //selected lecturers
        lecturersRef.orderByChild("courses/" + courseId).equalTo(true).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() != null) {
                    selectedLecturers.clear();
                    for (DataSnapshot d : snapshot.getChildren()) {
                        Lecturers lecturers = d.getValue(Lecturers.class);
                        selectedLecturers.add(lecturers);
                    }
                    //set lecturers view
                    lecturerAdapter.setLecturersList(selectedLecturers);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}