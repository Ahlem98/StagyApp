package com.example.stagyapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.auth.FirebaseAuth;

public class JobDetailsActivity extends AppCompatActivity {


    private Toolbar toolbar;


    private TextView mTitle;
    private TextView mDate;
    private TextView mDescription;
    private TextView mSkills;
    private TextView mSalary;
    DrawerLayout drawerLayout;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        toolbar = findViewById(R.id.toolbar_job_details);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        mTitle = findViewById(R.id.job_details_title);
        mDate = findViewById(R.id.job_details_date);
        mDescription = findViewById(R.id.job_details_description);
        mSkills = findViewById(R.id.job_details_skills);
        mSalary = findViewById(R.id.job_details_salary);

        mAuth= FirebaseAuth.getInstance();


        drawerLayout = findViewById(R.id.drawer_layout);



        //Receieve Data from all job activity using intent..

        Intent intent = getIntent();

        String title = intent.getStringExtra("title");
        String date = intent.getStringExtra("date");
        String description = intent.getStringExtra("description");
        String skills = intent.getStringExtra("skills");
        String salary = intent.getStringExtra("salary");

        mTitle.setText(title);
        mDate.setText(date);
        mDescription.setText(description);

        mSkills.setText(skills);
        mSalary.setText(salary);


    }public void ClickMenu(View view) {
        AllJobActivity.openDrawer(drawerLayout);

    }

    public void ClickLogo(View view) {
        AllJobActivity.closeDrawer(drawerLayout);


    }

    public void ClickHome(View view) {
        AllJobActivity.redirectActivity(this, AllJobActivity.class);


    }

    public void ClickFeedback(View view) {
        AllJobActivity.redirectActivity(this,Feedback.class);
    }
    public void ClickAboutUs(View view){
        recreate();

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
    protected void onPause(){
        super.onPause();
        AllJobActivity.closeDrawer(drawerLayout);

    }


}