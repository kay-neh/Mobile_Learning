package com.example.mobilelearning.ui.mycourses;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.mobilelearning.R;
import com.example.mobilelearning.data.BaseRepository;
import com.example.mobilelearning.ui.coursedetails.CourseInfoPostActivity;
import com.example.mobilelearning.data.pojo.Course;
import com.example.mobilelearning.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MyCourseFragment extends Fragment {

    RecyclerView recycle;
    MyCoursesAdapter myCoursesAdapter;

    public MyCourseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_course, container, false);

        // Access Repository
        BaseRepository baseRepository = new BaseRepository();

        recycle = view.findViewById(R.id.recycler_view_my_course);
        final RelativeLayout emptyView = view.findViewById(R.id.empty_layout);
        initAdapter();

        FirebaseAuth mFireBaseAuth = FirebaseAuth.getInstance();
        if(mFireBaseAuth.getCurrentUser()!= null) {
            //read list of my courses from firebase...
            baseRepository.getUserCourses(mFireBaseAuth.getCurrentUser().getUid()).observe(getViewLifecycleOwner(), new Observer<List<Course>>() {
                @Override
                public void onChanged(List<Course> courses) {
                    if(courses != null) {
                        myCoursesAdapter.setList(courses);
                        emptyView.setVisibility(View.GONE);
                        if(courses.isEmpty()){
                            emptyView.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
        }

        return view;
    }

    public void initAdapter(){
        final LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recycle.setLayoutManager(llm);
        recycle.setHasFixedSize(true);
        myCoursesAdapter = new MyCoursesAdapter(new MyCoursesAdapter.ListItemCourseClickListener() {
            @Override
            public void onListItemClick(Course course) {
                Intent intent = new Intent(getContext(), CourseInfoPostActivity.class);
                //passing data from fragment to activity......
                intent.putExtra(Constants.KEY_COURSE_ID, course.getCourseId());
                intent.putExtra(Constants.KEY_COURSE_TITLE, course.getCourseName());
                intent.putExtra(Constants.KEY_COURSE_CODE, course.getCourseCode());
                intent.putExtra(Constants.KEY_COURSE_IMAGE, course.getCoursePhoto());
                intent.putExtra(Constants.KEY_COURSE_DESCRIPTION, course.getCourseDescription());
                startActivity(intent);
            }
        });
        recycle.setAdapter(myCoursesAdapter);
    }

}