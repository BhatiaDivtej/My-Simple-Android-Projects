package com.example.tutorial1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText height = null;
    private EditText weight = null;
    private TextView result = null;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        result = findViewById(R.id.ans);
    }

    public void calculate(View view){
        float h = Float.parseFloat(height.getText().toString());
        float w = Float.parseFloat(weight.getText().toString());
        float bmi = w * 10000 / h / h;

        result.setText(""+bmi);

    }

}