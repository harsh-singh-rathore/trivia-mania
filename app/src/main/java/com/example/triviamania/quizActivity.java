package com.example.triviamania;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class quizActivity extends AppCompatActivity {

    ListView options;
    int score_of10 = 0;

    public void getQUestions() {
        DatabaseReference ref = FirebaseDatabase.getInstance("https://traviamania-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("questions/easy");
        Query getQues = ref.orderByChild("difficulty");
        getQues.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap: snapshot.getChildren()) {
                    Questionaire question = snap.getValue(Questionaire.class);
//                    String userName = user.getEmail().split("@")[0].replace('.','_');
//                    scoreCardArrayList.add(new ScoreCard(userName, user.getScore()));
//                    listAdapter.notifyDataSetChanged();
                   Log.d("what", "onDataChange: "+question.getQuestion());



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

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

        getQUestions();


        customOptionList optionListAdapter = new customOptionList(this, optionList);
        options.setAdapter(optionListAdapter);

    }
}