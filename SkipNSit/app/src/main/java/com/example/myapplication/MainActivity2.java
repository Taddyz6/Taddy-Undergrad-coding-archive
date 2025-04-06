package com.example.myapplication;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

//Navigation
public class MainActivity2 extends AppCompatActivity {
    public ImageButton Nav_btn;
    public ImageButton Left2;
    public ImageButton Mid2;
    public ImageButton Right2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Nav_btn = findViewById(R.id.nav_button);
        Left2 = findViewById(R.id.Left_btn2);
        Right2 = findViewById(R.id.Right_btn2);
        Mid2 = findViewById(R.id.Mid_btn2);
        Nav_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Uri gmmIntentUri = Uri.parse("google.navigation:q=37.4219911, -122.0861157&mode=w");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }



        });

        Left2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
                overridePendingTransition(R.anim.left_slide,R.anim.right_out);
            }
        });
        Mid2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity3();

            }
        });
        Right2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity4();
                overridePendingTransition(R.anim.left_out,R.anim.right_in);
            }
        });



    }
    public void openActivity2(){
        Intent intent=new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
    public void openActivity3(){
        Intent intent=new Intent(this, MainActivity3.class);
        startActivity(intent);
    }
    public void openActivity4(){
        Intent intent=new Intent(this, MainActivity4.class);
        startActivity(intent);
    }




}

