package com.example.stagyapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.auth.FirebaseAuth;

public class AboutUs extends AppCompatActivity {
    DrawerLayout drawerLayout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        mAuth=FirebaseAuth.getInstance();


        drawerLayout = findViewById(R.id.drawer_layout);
    }

    public void ClickMenu(View view) {
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