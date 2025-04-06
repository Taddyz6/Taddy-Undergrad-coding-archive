package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity4 extends AppCompatActivity {
    private ArrayList<Button> addButtons;
    private ArrayList<Button> removeButtons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        addButtons = new ArrayList<>();
        removeButtons = new ArrayList<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        storeAddButtons();
        storeRemoveButtons();

    }


    private void storeAddButtons() {
        Button button = findViewById(R.id.addButton1);
        addButtons.add(button);
        Button button2 = findViewById(R.id.addButton2);
        addButtons.add(button2);
        Button button3 = findViewById(R.id.addButton3);
        addButtons.add(button3);
        Button button4 = findViewById(R.id.addButton4);
        addButtons.add(button4);
        for (int i = 0; i < 4; i++) {
            int num = i + 1;
            addButtons.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateAmountNSum(num);
                }
            });
        }
    }

    private void storeRemoveButtons() {
        Button button = findViewById(R.id.removeButton1);
        removeButtons.add(button);
        Button button2 = findViewById(R.id.removeButton2);
        removeButtons.add(button2);
        Button button3 = findViewById(R.id.removeButton3);
        removeButtons.add(button3);
        Button button4 = findViewById(R.id.removeButton4);
        removeButtons.add(button4);
        for (int i = 0; i < 4; i++) {
            int num = i + 1;
            removeButtons.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    updateAmountNSub(num);
                }
            });
        }
    }

    private void updateAmountNSum (int num) {
        TextView text;
        double each;
        switch (num) {
            case 1:
                text = findViewById(R.id.amount1);
                each = 4.5;
                break;

            case 2:
                text = findViewById(R.id.amount2);
                each = 4;
                break;

            case 3:
                text = findViewById(R.id.amount3);
                each = 3.5;
                break;

            default:
                text = findViewById(R.id.amount4);
                each = 2;
                break;
        }
        double count = Double.parseDouble(text.getText().toString());
        count ++;
        String str = "" + count;
        text.setText(str);

        EditText total = findViewById(R.id.sum);
        double sum = Double.parseDouble(total.getText().toString().substring(8));
        sum += each;
        String ans = "Total: $" + sum;
        total.setText(ans);

    }

    private void updateAmountNSub (int num) {
        TextView text;
        double each;
        switch (num) {
            case 1:
                text = findViewById(R.id.amount1);
                each = 4.5;
                break;

            case 2:
                text = findViewById(R.id.amount2);
                each = 4;
                break;

            case 3:
                text = findViewById(R.id.amount3);
                each = 3.5;
                break;

            default:
                text = findViewById(R.id.amount4);
                each = 2;
                break;
        }
        double count = Double.parseDouble(text.getText().toString());
        count --;
        if (count < 0) {
            count = 0;
        }
        String str = "" + count;
        text.setText(str);

        EditText total = findViewById(R.id.sum);
        double sum = Double.parseDouble(total.getText().toString().substring(8));
        sum -= each;
        if (sum < 0) {
            sum = 0;
        }
        String ans = "Total: $" + sum;
        total.setText(ans);

    }
}