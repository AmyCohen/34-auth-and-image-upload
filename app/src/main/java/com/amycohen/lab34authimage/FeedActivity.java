package com.amycohen.lab34authimage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedActivity extends AppCompatActivity {

    public static final String TAG = "FIREBASE: ";

    @BindView(R.id.feed) RecyclerView feed;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FeedAdapter feedAdapter;

    //keep track of the users username
    private String mPhotos;

    List<Feed> allFeedItems;

    DatabaseReference mPublishedPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        ButterKnife.bind(this);

//        initializeUsername();

//         Read from the database
        mPublishedPhotos =  FirebaseDatabase.getInstance().getReference();
        mPublishedPhotos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Feed> photoItems = new ArrayList<>();

                for (DataSnapshot photo : dataSnapshot.getChildren()){
                    String photoKey = photo.getKey();
                    String imageUrl = photo.child("imageUrl").getValue(String.class);
                    String description = photo.child("description").getValue(String.class);
                    String uid = photo.child("uid").getValue(String.class);

                    Feed feedStatus = new Feed(photoKey, imageUrl, description, uid);
                    photoItems.add(feedStatus);
                }
                feedAdapter.replaceList(photoItems);
                feedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        attachListeners();
        linearLayoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(linearLayoutManager);

        allFeedItems = new ArrayList<>();
        feedAdapter = new FeedAdapter(allFeedItems);
//        recyclerView.setAdapter(feedAdapter);

        feed.setLayoutManager(linearLayoutManager);
        feed.setAdapter(feedAdapter);

    }

    @OnClick(R.id.takePicture)
    public void takePicture() {
        Intent intent = new Intent(this, PhotoUploadActivity.class);
        startActivity(intent);
    }

//    private void initializeUsername() {
//        Intent data = getIntent();
//        mPhotos = data.getStringExtra("photos");
//
//        mPublishedPhotos.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if(!dataSnapshot.hasChild(mPhotos)) {
//                    DatabaseReference userRef = mPublishedPhotos.child(mPhotos);
//                    userRef.child("status").setValue("online");
//                    userRef.child("statusText").setValue("");
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }

    public void attachListeners() {
        mPublishedPhotos =  FirebaseDatabase.getInstance().getReference();
        mPublishedPhotos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Feed> photoItems = new ArrayList<>();

                for (DataSnapshot photo : dataSnapshot.getChildren()){
                    String photoKey = photo.getKey();
                    String imageUrl = photo.child("imageUrl").getValue(String.class);
                    String description = photo.child("description").getValue(String.class);
                    String uid = photo.child("uid").getValue(String.class);

                    Feed feedStatus = new Feed(photoKey, imageUrl, description, uid);
                    photoItems.add(feedStatus);
                }
                feedAdapter.replaceList(photoItems);
                feedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

//    public void setStatus (String status) {
//        String username = mUsername;
//        String statusText = mEditText.getText().toString();
//
//        DatabaseReference user = mUsers.child(username);
//
//        user.child("status").setValue(status);
//        user.child("statusText").setValue(statusText);
//    }

}
