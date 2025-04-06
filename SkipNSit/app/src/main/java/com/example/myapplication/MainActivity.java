package com.example.myapplication;
import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.DialogInterface;


public class MainActivity extends AppCompatActivity {
    private ImageButton EnterCode;
    EditText code;
    private ArrayList<String> loginCodes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EnterCode=(ImageButton) findViewById(R.id.imageButton2);

        loginCodes = new ArrayList<>();
        loginCodes.add("123456");
        EnterCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authentication();
            }
        });
    }

    private void authentication () {
        EditText code =  findViewById(R.id.editTextTextPassword);
        String temp = code.getText().toString();
        if (loginCodes.contains(temp)) {
            openActivity2();
        } else {
            promptUser();
        }

    }
    public void openActivity2(){
        Intent intent=new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

    private void promptUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("You have entered the wrong code.");
        alertDialogBuilder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        alertDialogBuilder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.exit(0);
            }
        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

}



}

