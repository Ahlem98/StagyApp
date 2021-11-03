package com.example.stagyapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stagyapp.Model.Data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AllJobActivity extends AppCompatActivity {

    private Toolbar toolbar;
    DrawerLayout drawerLayout;

    //Recycler
    private RecyclerView recyclerView;

    //Firebase
    private FirebaseAuth mAuth;

    private DatabaseReference mAllStagePost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_job);

        drawerLayout =findViewById(R.id.drawer_layout);

        toolbar = findViewById(R.id.all_job_post);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        //Database

        mAuth=FirebaseAuth.getInstance();

        mAllStagePost = FirebaseDatabase.getInstance().getReference().child("Public database");
        mAllStagePost.keepSynced(true);

        recyclerView = findViewById(R.id.recycler_all_job);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

    }

    public void  ClickMenu(View view){

        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
        

    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public void ClickHome(View view){
        recreate();
    }

    public void ClickFeedback(View view){
        redirectActivity(this,Feedback.class);
        
    }
    public void ClickAboutUs(View view){
        redirectActivity(this,AboutUs.class);

    }
    public void ClickLogout(View view){
        logout(this);

    }

    public void logout(Activity activity) {
        AlertDialog.Builder builder= new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
                activity.finishAffinity();
                System.exit(0);

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        builder.show();
    }

    public static void redirectActivity(Activity activity,Class aclass) {
        Intent intent=new Intent(activity,aclass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }
    protected void onPause(){
        super.onPause();
        closeDrawer(drawerLayout);
    }


    @Override
    protected void onStart(){
        super.onStart();

        FirebaseRecyclerOptions<Data> firebaseRecyclerOptions =
                new FirebaseRecyclerOptions.Builder<Data>()
                        .setQuery(mAllStagePost, Data.class)
                        .build();

        FirebaseRecyclerAdapter<Data, AllJobPostViewHolder> adapter = new FirebaseRecyclerAdapter<Data, AllJobPostViewHolder>(firebaseRecyclerOptions) {

            @NonNull
            @Override
            public AllJobPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alljobpost,parent,false);
                return new AllJobPostViewHolder(view);
            }


            protected void onBindViewHolder(@NonNull AllJobPostViewHolder viewHolder, int i, @NonNull final Data model) {

                viewHolder.setJobTitle(model.getTitle());
                viewHolder.setJobDate(model.getDate());
                viewHolder.setJobDescription(model.getDescription());
                viewHolder.setJobSkills(model.getSkills());
                viewHolder.setJobSalary(model.getSalary());

                viewHolder.myview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(), JobDetailsActivity.class);

                        intent.putExtra("title",model.getTitle());
                        intent.putExtra("date", model.getDate());
                        intent.putExtra("description",model.getDescription());
                        intent.putExtra("skills",model.getSkills());
                        intent.putExtra("salary",model.getSalary());

                        startActivity(intent);


                    }
                });

            }


        };


        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }

    public static class AllJobPostViewHolder extends RecyclerView.ViewHolder{

        View myview;

        public AllJobPostViewHolder(@NonNull View itemView) {
            super(itemView);
            myview=itemView;
        }

        public void setJobTitle(String title){
            TextView mTitle = myview.findViewById(R.id.all_job_post_title);
            mTitle.setText(title);
        }

        public void setJobDate(String date){
            TextView mDate = myview.findViewById(R.id.all_job_post_date);
            mDate.setText(date);
        }

        public void setJobDescription(String description){
            TextView mDescription = myview.findViewById(R.id.all_job_post_description);
            mDescription.setText(description);
        }

        public void setJobSkills(String skills){
            TextView mSkills = myview.findViewById(R.id.all_job_post_skills);
            mSkills.setText(skills);
        }

        public void setJobSalary(String salary){
            TextView mSalary = myview.findViewById(R.id.all_job_post_salary);
            mSalary.setText(salary);
        }


    }


}