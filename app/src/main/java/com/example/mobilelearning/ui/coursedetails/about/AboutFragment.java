package com.example.mobilelearning.ui.coursedetails.about;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mobilelearning.R;
import com.example.mobilelearning.data.BaseRepository;
import com.example.mobilelearning.utils.Constants;
import com.example.mobilelearning.utils.LecturerAdapter;
import com.example.mobilelearning.data.pojo.Lecturer;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    RecyclerView lectRecyclerView;
    LecturerAdapter lecturerAdapter;

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        //retrieve data from host activity
        String courseId = getArguments().getString(Constants.KEY_COURSE_ID);
        String courseDescription = getArguments().getString(Constants.KEY_COURSE_DESCRIPTION);

        TextView courseDes = view.findViewById(R.id.course_description_post_reg);
        lectRecyclerView = view.findViewById(R.id.lect_recycler_view);

        // Access Repository
        final BaseRepository baseRepository = new BaseRepository();

        initAdapter();

        courseDes.setText(courseDescription);

        baseRepository.getLecturers(courseId).observe(getViewLifecycleOwner(), new Observer<List<Lecturer>>() {
            @Override
            public void onChanged(List<Lecturer> lecturers) {
                if(lecturers != null){
                    lecturerAdapter.setList(lecturers);
                }
            }
        });

    return view;
    }

    public void initAdapter(){
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        lectRecyclerView.setLayoutManager(llm);
        lectRecyclerView.setHasFixedSize(true);
        lecturerAdapter = new LecturerAdapter();
        lectRecyclerView.setAdapter(lecturerAdapter);
    }

}
