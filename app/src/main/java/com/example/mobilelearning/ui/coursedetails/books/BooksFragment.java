package com.example.mobilelearning.ui.coursedetails.books;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mobilelearning.R;
import com.example.mobilelearning.data.BaseRepository;
import com.example.mobilelearning.data.pojo.Book;
import com.example.mobilelearning.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;


import java.io.File;
import java.util.List;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

/**
 * A simple {@link Fragment} subclass.
 */
public class BooksFragment extends Fragment {

    String bookFileName, bookFileExtension;
    long downloadID;
    RecyclerView bookRecyclerView;
    BookAdapter bookAdapter;
    FirebaseAuth firebaseAuth;
    BaseRepository baseRepository;

    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Fetching the download id received with the broadcast
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            //Checking if the received broadcast is for our enqueued download by matching download id
            if (downloadID == id) {
                bookAdapter.refreshAdapter();
                Toast.makeText(getContext(), "Download Completed", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public BooksFragment() {
        // Required empty public constructor
    }


    @SuppressLint("UnspecifiedRegisterReceiverFlag")
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
                if (books != null) {
                    bookAdapter.setList(books);
                }
            }
        });

        requireActivity().registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        return view;
    }

    public void initAdapter() {
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        bookRecyclerView.setLayoutManager(llm);
        bookRecyclerView.setHasFixedSize(true);
        bookAdapter = new BookAdapter(new BookAdapter.ListItemClickListener() {
            @Override
            public void onListItemClick(Book book) {
                // Create a file
                File file = createFileFromBook(book);
                String fileType = getFileType(book);
                //Opens the downloaded book with an intent if present else it downloads the book
                if (file.exists()) {
                    // Open with intent
                    //start an intent to open the book
                    Intent openBook = new Intent(Intent.ACTION_VIEW);
                    Uri bookUri = FileProvider.getUriForFile(getContext(), "com.example.mobilelearning.provider", file);
                    openBook.setDataAndType(bookUri, fileType);
                    openBook.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    openBook.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    //using an intent picker.
                    Intent intent = Intent.createChooser(openBook, "Open File");
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        // Instruct the user to install a PDF reader here, or something
                    }
                } else {
                    // Download it first
                    baseRepository.getBookDownloadUrl(book).observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(String downloadUrl) {
                            if (downloadUrl != null) {
                                downloadFile(getContext(), bookFileName, bookFileExtension, DIRECTORY_DOWNLOADS, downloadUrl);
                                Toast.makeText(getContext(), "Downloading...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        bookRecyclerView.setAdapter(bookAdapter);
    }

    public File createFileFromBook(Book book) {
        String bookFilePath = "/Android/data/com.example.mobilelearning/files/Download/";
        String bookFileTitle = book.getBookName();
        return new File(Environment.getExternalStorageDirectory() + bookFilePath + bookFileTitle);
    }

    public String getFileType(Book book) {
        String bookFileTitle = book.getBookName();
        String[] bookArray = bookFileTitle.split("\\.(?=[^\\.]+$)");
        bookFileName = bookArray[0];
        bookFileExtension = "." + bookArray[1];
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
        return fileType;
    }

    //Helper method to call android's default download manager....
    public void downloadFile(Context context, String filename, String fileExtension, String destinationDirectory, String url) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, filename + fileExtension);
        downloadID = downloadManager.enqueue(request);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        requireActivity().unregisterReceiver(onDownloadComplete);
    }

}
