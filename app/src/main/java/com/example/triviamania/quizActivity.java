package com.example.triviamania;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class quizActivity extends AppCompatActivity {

    ListView options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        options = findViewById(R.id.optionsList);

        Intent intent = getIntent();
        String difficulty = intent.getStringExtra("difficulty");

        ArrayList<String> optionList = new ArrayList<>();
        optionList.add("Harsh");
        optionList.add("Harry");
        optionList.add("Rohan");
        optionList.add("Sameer");


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.simple_list)

    }
}