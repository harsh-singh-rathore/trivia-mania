package com.example.triviamania;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class quizActivity extends AppCompatActivity {

    ListView options;
    int score_of10 = 0;
    int questions_attempted = 0;
    public ArrayList<Questionaire> questionaireArrayList = new ArrayList<>();
    public ArrayList<String> optionList = new ArrayList<>();
    customOptionList optionListAdapter;
    Questionaire curQuestion;
    TextView questionTextView;

    public void getQuestions() {
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
                    if(question != null) {
                        questionaireArrayList.add(question);
                        Log.d("what", "onDataChange: "+questionaireArrayList.get(0).getQuestion().replaceAll("&quot;", "\""));
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Log.d("OMG", "onDataChange: "+Integer.toString(questionaireArrayList.size()));
    }

    public void assignQuestions(int i, ArrayList<Questionaire> questionaireArrayList) {
        if(i<3) {

//            curQuestion = questionaireArrayList.get(i);

            optionList.clear();

            Log.d("HERE ERROR", "assignQuestions: Questions are getting assigned");
            optionList.add(questionaireArrayList.get(i).getOptiona());

            optionList.add(questionaireArrayList.get(i).getOptionb());
            optionList.add(questionaireArrayList.get(i).getOptionc());
            optionList.add(questionaireArrayList.get(i).getOptiond());


            questionTextView.setText(questionaireArrayList.get(i).getQuestion());
            optionListAdapter.notifyDataSetChanged();
        } else {
            optionList.clear();


            questionTextView.setText("");
            optionListAdapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), "Questions Finished", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        options = findViewById(R.id.optionsList);
        questionTextView = findViewById(R.id.questionTextView);

        Intent intent = getIntent();
        String difficulty = intent.getStringExtra("difficulty");


        // Getting questions here
        DatabaseReference ref = FirebaseDatabase.getInstance("https://traviamania-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("questions/easy");
        Query getQues = ref.orderByChild("difficulty");
        getQues.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap: snapshot.getChildren()) {
                    Questionaire question = snap.getValue(Questionaire.class);
                    if(question != null) {
                        question.formatQ();
                        questionaireArrayList.add(question);
                        Log.d("what", "onDataChange: "+questionaireArrayList.get(0).getQuestion().replaceAll("&quot;", "\""));
                    }
                }
                // start code here

                optionList.add(difficulty);
                optionListAdapter = new customOptionList(quizActivity.this, optionList);
                options.setAdapter(optionListAdapter);
                Log.d("OMG", "onCreate: "+Integer.toString(questionaireArrayList.size()));

                options.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if(questionaireArrayList.get(questions_attempted).checkRightAns(optionList.get(i))) {
                            score_of10+=1;
                        }
                        questions_attempted+=1;
                        assignQuestions(questions_attempted, questionaireArrayList);
                    }
                });


                Collections.shuffle(questionaireArrayList);
                assignQuestions(0, questionaireArrayList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // done getting questions here








    }
}