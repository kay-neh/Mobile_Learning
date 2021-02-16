package com.example.mobilelearning.utils;

import android.content.Context;
import android.os.Environment;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mobilelearning.R;

import java.io.File;
import java.util.List;

public class BooksAdapter extends ArrayAdapter<Books> {
    public BooksAdapter(@NonNull Context context, @NonNull List<Books> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.books_fragment_list_view,parent,false);
        }
        //Updating the ui
        Books currentBook = getItem(position);
        TextView bookTitle = listItemView.findViewById(R.id.book_title);
        TextView bookSize = listItemView.findViewById(R.id.book_size);
        ImageView downloadIcon = listItemView.findViewById(R.id.download);

        bookTitle.setText(currentBook.getBookName());
        bookSize.setText(Formatter.formatShortFileSize(getContext(),Long.parseLong(currentBook.getBookSize())));

        //sets the download icon dynamically based on book availability on device
        String bookFilePath = "/Android/data/com.example.mobilelearning/files/Download/";
        String bookFileName = currentBook.getBookName();
        File file = new File(Environment.getExternalStorageDirectory()+ bookFilePath + bookFileName);

        if (file.exists()){
            downloadIcon.setImageResource(R.drawable.baseline_offline_pin_24);
        }else{
            downloadIcon.setImageResource(R.drawable.round_get_app_24);
        }

        return listItemView;
    }
}
