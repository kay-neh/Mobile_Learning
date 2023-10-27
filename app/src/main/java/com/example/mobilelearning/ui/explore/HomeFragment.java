package com.example.mobilelearning.ui.explore;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobilelearning.data.BaseRepository;
import com.example.mobilelearning.ui.course.CourseInfoPreActivity;
import com.example.mobilelearning.R;
import com.example.mobilelearning.data.pojo.Course;
import com.example.mobilelearning.utils.Constants;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    RecyclerView recycle;
    ExploreAdapter exploreAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Access Repository
        BaseRepository baseRepository = new BaseRepository();

        recycle = view.findViewById(R.id.recycler_view_explore);
        initAdapter();

        baseRepository.getCourses().observe(getViewLifecycleOwner(), new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                if(courses != null){
                    exploreAdapter.setList(courses);
                }
            }
        });

        return view;
    }

    public void initAdapter(){
        GridLayoutManager glm = new GridLayoutManager(getContext(),2);
        int spacing = 10;
        recycle.setPadding(spacing,spacing,spacing,spacing);
        recycle.setClipToPadding(false);
        recycle.setClipChildren(false);
        recycle.addItemDecoration(new ExploreItemDecoration(spacing));
        recycle.setLayoutManager(glm);
        recycle.setHasFixedSize(true);
        exploreAdapter = new ExploreAdapter(new ExploreAdapter.ListItemClickListener() {
            @Override
            public void onListItemClick(String courseId) {
                Intent intent = new Intent(getContext(), CourseInfoPreActivity.class);
                //passing data from fragment to activity......
                intent.putExtra(Constants.KEY_COURSE_ID, courseId);
                startActivity(intent);
            }
        });
        recycle.setAdapter(exploreAdapter);
    }

}