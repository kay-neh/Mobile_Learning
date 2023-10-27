package com.example.mobilelearning.ui.coursedetails.program;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobilelearning.R;
import com.example.mobilelearning.data.BaseRepository;
import com.example.mobilelearning.data.pojo.CourseOutline;
import com.example.mobilelearning.utils.Constants;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProgramFragment extends Fragment {

    RecyclerView programRecyclerView;
    ProgramAdapter programAdapter;

    public ProgramFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_program, container, false);

        programRecyclerView = view.findViewById(R.id.program_recycler_view);
        initAdapter();

        //retrieve data from host activity
        String courseId = getArguments().getString(Constants.KEY_COURSE_ID);

        // Access Repository
        final BaseRepository baseRepository = new BaseRepository();

        baseRepository.getCourseOutlines(courseId).observe(getViewLifecycleOwner(), new Observer<List<CourseOutline>>() {
            @Override
            public void onChanged(List<CourseOutline> courseOutlines) {
                if(courseOutlines != null){
                    programAdapter.setList(courseOutlines);
                }
            }
        });

        return view;
    }

    public void initAdapter(){
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        programRecyclerView.setLayoutManager(llm);
        programRecyclerView.setHasFixedSize(true);
        programAdapter = new ProgramAdapter();
        programRecyclerView.setAdapter(programAdapter);
    }

}
