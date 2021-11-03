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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostJobActivity extends AppCompatActivity {

    private FloatingActionButton fabBtn;
    private Toolbar toolbar;
    DrawerLayout drawerLayout;

    //Recycler View

    private RecyclerView recyclerView;

    //Firebase

    private FirebaseAuth mAuth;
    private DatabaseReference StagePostDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);
        drawerLayout =(DrawerLayout)findViewById(R.id.drawer_layout1);

        toolbar = findViewById(R.id.toolbar_post_job);
        setSupportActionBar(toolbar);


        getSupportActionBar().setTitle("");




        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        fabBtn = findViewById(R.id.fab_add);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser mUser = mAuth.getCurrentUser();
        String uId = mUser.getUid();

        StagePostDatabase = FirebaseDatabase.getInstance().getReference().child("Internship Post").child(uId);


        recyclerView = findViewById(R.id.recycler_job_post_id);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), InsertJobPostActivity.class));

            }
        });

    }
    public void ClickMenu(View view) {
        openDrawer(drawerLayout);

    }public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view) {
        AllJobActivity.closeDrawer(drawerLayout);


    }

    public void ClickHome(View view) {
        recreate();



    }

    public void ClickFeedback(View view) {
        AllJobActivity.redirectActivity(this,Feedback.class);
    }
    public void ClickAboutUs(View view){
        AllJobActivity.redirectActivity(this, AboutUs.class);

    }
    public void ClickLogout(View view){
        logout(this);

    }public void logout(Activity activity) {
        AlertDialog.Builder builder= new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public FirebaseAuth mAuth;

            @Override
            public void onClick(DialogInterface dialog, int which) {
              //  mAuth.signOut();
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
    protected void onPause(){
        super.onPause();
        AllJobActivity.closeDrawer(drawerLayout);

    }
    @Override
    protected void onStart(){
        super.onStart();

        FirebaseRecyclerOptions<Data> firebaseRecyclerOptions =
                new FirebaseRecyclerOptions.Builder<Data>().setQuery(StagePostDatabase, Data.class).build();


        FirebaseRecyclerAdapter<Data, MyViewHolder> adapter = new FirebaseRecyclerAdapter<Data, MyViewHolder>(firebaseRecyclerOptions) {

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_post_item,parent,false);
                return new MyViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i, @NonNull Data model) {

                viewHolder.setJobTitle(model.getTitle());
                viewHolder.setJobDate(model.getDate());
                viewHolder.setJobDescription(model.getDescription());
                viewHolder.setJobSkills(model.getSkills());
                viewHolder.setJobSalary(model.getSalary());
            }

        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        View myview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myview = itemView;
        }

        public void setJobTitle(String title){

            TextView mTitle = myview.findViewById(R.id.job_title);
            mTitle.setText(title);

        }

        public void setJobDate(String date){
            TextView mDate = myview.findViewById(R.id.job_date);
            mDate.setText(date);
        }

        public void setJobDescription(String description){
            TextView mDescription = myview.findViewById(R.id.job_description);
            mDescription.setText(description);
        }

        public void setJobSkills(String skills){
            TextView mSkills = myview.findViewById(R.id.job_skills);
            mSkills.setText(skills);
        }

        public void setJobSalary(String salary){
            TextView mSalary = myview.findViewById(R.id.job_salary);
            mSalary.setText(salary);
        }
    }
}

