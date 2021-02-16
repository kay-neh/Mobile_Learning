package com.example.mobilelearning.utils;

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

import java.util.List;

public class MyCoursesAdapter extends RecyclerView.Adapter<MyCoursesAdapter.MyCourseViewHolder>{

    public interface ListItemCourseClickListener{
        void onListItemClick(int index);
    }

    List<Courses> mycourse;
    final private ListItemCourseClickListener mCourseOnclickListener;

    public MyCoursesAdapter(List<Courses> mycourse, MyCoursesAdapter.ListItemCourseClickListener mCourseOnclickListener) {
        this.mycourse = mycourse;
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
                    mCourseOnclickListener.onListItemClick(clickedPosition);
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
        if(mycourse.get(position).getCoursePhoto() != null) {
            Glide.with(holder.image.getContext()).load(mycourse.get(position).getCoursePhoto()).into(holder.image);
        }
        holder.txt1.setText(mycourse.get(position).getCourseCode());
        holder.txt2.setText(mycourse.get(position).getCourseName());
    }

    public int getItemCount() {
        return mycourse.size();
    }

    public String getCourseItemId(int index){
        return mycourse.get(index).getCourseId();
    }

}
