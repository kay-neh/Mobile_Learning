package com.example.mobilelearning.ui.coursedetails.books;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mobilelearning.R;
import com.example.mobilelearning.data.BaseRepository;
import com.example.mobilelearning.data.pojo.Book;
import com.example.mobilelearning.utils.Constants;
import com.example.mobilelearning.utils.LecturerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

/**
 * A simple {@link Fragment} subclass.
 */
public class BooksFragment extends Fragment {

    RecyclerView bookRecyclerView;
    BookAdapter bookAdapter;
    FirebaseAuth firebaseAuth;
    BaseRepository baseRepository;

    public BooksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_books, container, false);

        bookRecyclerView = view.findViewById(R.id.book_recycler_view);
        initAdapter();

        //retrieve data from host activity
        String courseId = getArguments().getString(Constants.KEY_COURSE_ID);

        firebaseAuth = FirebaseAuth.getInstance();

        // Access Repository
        baseRepository = new BaseRepository();

        baseRepository.getBooks(courseId).observe(getViewLifecycleOwner(), new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                if(books != null){
                    bookAdapter.setList(books);
                }
            }
        });

    return view;
    }

    public void initAdapter(){
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        bookRecyclerView.setLayoutManager(llm);
        bookRecyclerView.setHasFixedSize(true);
        bookAdapter = new BookAdapter(new BookAdapter.ListItemClickListener() {
            @Override
            public void onListItemClick(Book book) {
                String bookFilePath = "/Android/data/com.example.mobilelearning/files/Download/";
                String bookFileTitle = book.getBookName();
                String [] bookArray = bookFileTitle.split("\\.(?=[^\\.]+$)");
                final String bookFileName = bookArray[0];
                final String bookFileExtension = "." + bookArray[1];
                String fileType = "";
                switch (bookFileExtension) {
                    case ".pdf":
                        fileType = "application/pdf";
                        break;
                    case ".doc":
                        fileType = "application/msword";
                        break;
                    case ".docx":
                        fileType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
                        break;
                    case ".text":
                        fileType = "text/*";
                        break;
                    case ".xls":
                        fileType = "application/vnd.ms-excel";
                        break;
                }
                //here now
                File file = new File(Environment.getExternalStorageDirectory()+ bookFilePath + bookFileTitle);
                //Opens the downloaded book with an intent if present else it downloads the book
                if (file.exists()){
                    // Open with intent
                    bookAdapter.notifyDataSetChanged();
                    //start an intent to open the book
                    Intent openBook = new Intent(Intent.ACTION_VIEW);
                    Uri bookUri = FileProvider.getUriForFile(getContext(),"com.example.mobilelearning.provider",file);
                    openBook.setDataAndType(bookUri,fileType);
                    openBook.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    openBook.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    //using an intent picker.
                    Intent intent = Intent.createChooser(openBook, "Open File");
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        // Instruct the user to install a PDF reader here, or something
                    }
                }else{
                    // Download it first
                    baseRepository.getBookDownloadUrl(book).observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            if(s != null){
                                downloadFile(getContext(), bookFileName, bookFileExtension, DIRECTORY_DOWNLOADS, s);
                                Toast.makeText(getContext(),"Downloading...",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        bookRecyclerView.setAdapter(bookAdapter);
    }

    //Helper method to call android's default download manager....
    public void downloadFile(Context context, String filename, String fileExtension, String destinationDirectory, String url){
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context,destinationDirectory,filename+fileExtension);
        downloadManager.enqueue(request);
    }
}