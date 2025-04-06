package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity3 extends AppCompatActivity {
    public ImageButton Left3;
    public ImageButton Mid3;
    public ImageButton Right3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button btnCam;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Left3 = findViewById(R.id.Left_btn3);
        Right3 = findViewById(R.id.Right_btn3);
        Mid3 = findViewById(R.id.Mid_btn3);
        Left3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
            }
        });
        Mid3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity3();
            }
        });
        Right3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity4();
            }
        });

        btnCam = (Button) findViewById(R.id.btnCam);
        btnCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent();
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivity(intent);
                }catch(Exception e)
                {
                    e.printStackTrace();
                }


            }});

    }
    public void openActivity2(){
        Intent intent=new Intent(this, MainActivity2.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_slide,R.anim.right_out);
    }
    public void openActivity3(){
        Intent intent=new Intent(this, MainActivity3.class);
        startActivity(intent);
    }
    public void openActivity4(){
        Intent intent=new Intent(this, MainActivity4.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_out,R.anim.right_in);
    }


}