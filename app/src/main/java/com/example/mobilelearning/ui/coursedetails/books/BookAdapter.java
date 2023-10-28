package com.example.mobilelearning.ui.coursedetails.books;

import android.content.Context;
import android.os.Environment;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilelearning.R;
import com.example.mobilelearning.data.pojo.Book;

import java.io.File;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookVH>{

    List<Book> books;
    final private ListItemClickListener mOnclickListener;

    public interface ListItemClickListener{
        void onListItemClick(Book book);
    }

    public BookAdapter(ListItemClickListener mOnclickListener){
        this.mOnclickListener = mOnclickListener;
    }

    @NonNull
    @Override
    public BookVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.books_fragment_list_view, parent, false);
        return new BookVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookVH holder, int position) {
        if (books != null) {
            Book book = books.get(position);
            holder.bookTitle.setText(book.getBookName());
            holder.bookSize.setText(Formatter.formatShortFileSize(holder.itemView.getContext(), Long.parseLong(book.getBookSize())));

            //sets the download icon dynamically based on book availability on device
            String bookFilePath = "/Android/data/com.example.mobilelearning/files/Download/";
            String bookFileName = book.getBookName();
            File file = new File(Environment.getExternalStorageDirectory()+ bookFilePath + bookFileName);

            if (file.exists()){
                holder.downloaded.setImageResource(R.drawable.baseline_offline_pin_24);
            }else{
                holder.downloaded.setImageResource(R.drawable.baseline_download_for_offline_24);
            }

        }
    }

    @Override
    public int getItemCount() {
        if (books != null)
            return books.size();
        else return 0;
    }

    public void setList(List<Book> books){
        this.books = books;
        notifyDataSetChanged();
    }

    public void refreshAdapter(){
        notifyDataSetChanged();
    }

    class BookVH extends RecyclerView.ViewHolder{
        ImageView downloaded;
        TextView bookTitle, bookSize;
        BookVH(@NonNull View itemView) {
            super(itemView);
            downloaded = itemView.findViewById(R.id.download);
            bookTitle = itemView.findViewById(R.id.book_title);
            bookSize = itemView.findViewById(R.id.book_size);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clickedPosition = getAdapterPosition();
                    mOnclickListener.onListItemClick(books.get(clickedPosition));
                }
            });
        }
    }




}
