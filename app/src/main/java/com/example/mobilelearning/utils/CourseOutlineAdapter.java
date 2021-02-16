package com.example.mobilelearning.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mobilelearning.R;

import java.util.List;

public class CourseOutlineAdapter extends ArrayAdapter<CourseOutline> {
    public CourseOutlineAdapter(@NonNull Context context, @NonNull List<CourseOutline> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.program_fragment_list_view,parent,false);
        }
        //Updating the ui
        CourseOutline currentCourseOutline = getItem(position);
        TextView courseOutlineIndex = listItemView.findViewById(R.id.course_outline_index_no);
        TextView courseOutlineTitle = listItemView.findViewById(R.id.course_outline_title);
        TextView courseOutlineDesc = listItemView.findViewById(R.id.course_outline_description);

        courseOutlineIndex.setText(String.valueOf(position + 1));
        courseOutlineTitle.setText(currentCourseOutline.getCourseOutlineTitle());
        courseOutlineDesc.setText(currentCourseOutline.getCourseOutlineDesc());

        return listItemView;
    }
}
