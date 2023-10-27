package com.example.mobilelearning.ui.mycourses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobilelearning.R;
import com.example.mobilelearning.data.pojo.Course;

import java.util.List;

public class MyCoursesAdapter extends RecyclerView.Adapter<MyCoursesAdapter.MyCourseViewHolder>{

    List<Course> myCourse;
    final private ListItemCourseClickListener mCourseOnclickListener;

    public interface ListItemCourseClickListener{
        void onListItemClick(Course course);

    }

    public MyCoursesAdapter(MyCoursesAdapter.ListItemCourseClickListener mCourseOnclickListener) {
        this.mCourseOnclickListener = mCourseOnclickListener;
    }

    class MyCourseViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        TextView txt1, txt2;
        ImageView image;

        public MyCourseViewHolder(final View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.my_course_card_view);
            txt1 = itemView.findViewById(R.id.my_course_code);
            txt2 = itemView.findViewById(R.id.my_course_title);
            image = itemView.findViewById(R.id.my_course_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clickedPosition = getAdapterPosition();
                    mCourseOnclickListener.onListItemClick(myCourse.get(clickedPosition));
                }
            });
        }
    }

    @NonNull
    @Override
    public MyCourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.my_course_fragment_recycler_view, parent, false);
        MyCourseViewHolder mvh = new MyCourseViewHolder(view);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyCourseViewHolder holder, int position) {
        if(myCourse.get(position).getCoursePhoto() != null) {
            Glide.with(holder.image.getContext()).load(myCourse.get(position).getCoursePhoto()).into(holder.image);
        }
        holder.txt1.setText(myCourse.get(position).getCourseCode());
        holder.txt2.setText(myCourse.get(position).getCourseName());
    }

    public int getItemCount() {
        if (myCourse != null){
            return myCourse.size();
        } else {
            return 0;
        }
    }

    public void setList(List<Course> course){
        myCourse = course;
        notifyDataSetChanged();
    }

}
