package com.example.mobilelearning;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mobilelearning.utils.Books;
import com.example.mobilelearning.utils.BooksAdapter;
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

    FirebaseAuth firebaseAuth;

    public BooksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_books, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final ListView booksListView = view.findViewById(R.id.books_list_view);

        //retrieve data from host activity
        String courseId = getArguments().getString("Course Id");

        DatabaseReference databaseReference = firebaseDatabase.getReference("books");
        databaseReference.orderByChild("courses/" + courseId).equalTo(true).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<Books> books = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Books book = dataSnapshot1.getValue(Books.class);
                    books.add(book);
                }

                final BooksAdapter booksAdapter = new BooksAdapter(getContext(),books);
                booksListView.setAdapter(booksAdapter);
                //Download books to internal storage and/or opens it in any pdf viewer with an intent
                booksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        String bookFilePath = "/Android/data/com.example.mobilelearning/files/Download/";
                        String bookFileTitle = books.get(position).getBookName();
                        String [] book = bookFileTitle.split("\\.(?=[^\\.]+$)");
                        final String bookFileName = book[0];
                        final String bookFileExtension = "." + book[1];
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
                            booksAdapter.notifyDataSetChanged();
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
                            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                            StorageReference storageReference = firebaseStorage.getReferenceFromUrl(books.get(position).getBookDownloadUrl());
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String downloadUrl = uri.toString();
                                downloadFile(getContext(), bookFileName, bookFileExtension, DIRECTORY_DOWNLOADS, downloadUrl);
                                Toast.makeText(getContext(),"Downloading...",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }//end of else
                }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    return view;
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