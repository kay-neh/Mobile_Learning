package com.example.mobilelearning;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobilelearning.utils.Courses;
import com.example.mobilelearning.utils.MyCoursesAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyCourseFragment extends Fragment {

    public MyCourseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_course, container, false);

        //Create myCourseList from database info here.......
        List<Courses> myCourses = new ArrayList<>();
        RecyclerView myCourserecycle = view.findViewById(R.id.recycler_view_my_course);
        if(myCourses.size()==0){
            myCourserecycle.setVisibility(View.GONE);
        }

        //Set up recycler View here
         final MyCoursesAdapter myadapt = new MyCoursesAdapter(myCourses, new MyCoursesAdapter.ListItemCourseClickListener() {
            @Override
            public void onListItemClick(int index) {

            }
        });

        return view;
    }
}
