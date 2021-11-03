package com.example.stagyapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.stagyapp.Model.Data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class InsertJobPostActivity extends AppCompatActivity {

    private Toolbar toolbar1;
    DrawerLayout drawerLayout;

    private EditText job_title;
    private EditText job_description;
    private EditText job_skills;
    private EditText job_salary;
    private Button btn_post_job;

    //Firebase Auth

    private FirebaseAuth mAuth;
    private DatabaseReference mStagePost;

    private DatabaseReference mPublicDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_job_post);
        drawerLayout =findViewById(R.id.drawer_layout);


        toolbar1 = findViewById(R.id.insert_job_toolbar);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setTitle("");

        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser mUser = mAuth.getCurrentUser();
        String uId = mUser.getUid();

        mStagePost = FirebaseDatabase.getInstance().getReference().child("Internship Post").child(uId);
        mPublicDatabase = FirebaseDatabase.getInstance().getReference().child("Public database");


        InsertJob();
    }public void ClickMenu(View view) {
        AllJobActivity.openDrawer(drawerLayout);

    }

    public void ClickLogo(View view) {
        closeDrawer(drawerLayout);


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
    } protected void onPause() {

        super.onPause();
        closeDrawer(drawerLayout);

    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }


    private void InsertJob() {

        job_title = findViewById(R.id.job_title);
        job_description = findViewById(R.id.job_description);
        job_skills = findViewById(R.id.job_skill);
        job_salary = findViewById(R.id.job_salary);


        btn_post_job = findViewById(R.id.btn_job_post);

        btn_post_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = job_title.getText().toString().trim();
                String description = job_description.getText().toString().trim();
                String skills = job_skills.getText().toString().trim();
                String salary = job_salary.getText().toString().trim();

                if(TextUtils.isEmpty(title)){
                    job_title.setError("Required Field..");
                    return;
                }

                if(TextUtils.isEmpty(description)){
                    job_description.setError("Required Field..");
                    return;
                }

                if(TextUtils.isEmpty(skills)){
                    job_skills.setError("Required Field..");
                    return;
                }

                if(TextUtils.isEmpty(salary)){
                    job_salary.setError("Required Field..");
                    return;
                }

                String id = mStagePost.push().getKey();

                String date = DateFormat.getDateInstance().format(new Date());

                Data data = new Data(title, description, skills, salary, id, date);

                mStagePost.child(id).setValue(data);

                mPublicDatabase.child(id).setValue(data);

                Toast.makeText(getApplicationContext(),"Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), PostJobActivity.class));




            }
        });
    }





}