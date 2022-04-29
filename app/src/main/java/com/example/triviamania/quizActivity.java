package com.example.triviamania;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class quizActivity extends AppCompatActivity {

    ListView options;
    int score_of10 = 0;
    int questions_attempted = 0;
    public ArrayList<Questionaire> questionaireArrayList = new ArrayList<>();
    public ArrayList<String> optionList = new ArrayList<>();
    customOptionList optionListAdapter;
    Questionaire curQuestion;
    Button floatingButton;
    TextView questionTextView;
    String googleSearch;
    TextView linkTextView;
    TextView noOfQuestionTV;
    AnyChartView anyChartView;
    LinearLayout quiz, chart;


    public void assignQuestions(int i, ArrayList<Questionaire> questionaireArrayList) {
        if(i<10) {

//            curQuestion = questionaireArrayList.get(i);

            optionList.clear();

            Log.d("HERE ERROR", "assignQuestions: Questions are getting assigned");
            optionList.add(questionaireArrayList.get(i).getOptiona());
            optionList.add(questionaireArrayList.get(i).getOptionb());
            optionList.add(questionaireArrayList.get(i).getOptionc());
            optionList.add(questionaireArrayList.get(i).getOptiond());
            questionTextView.setText(questionaireArrayList.get(i).getQuestion());
            Collections.shuffle(optionList);
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
        floatingButton = findViewById(R.id.floatingButton);
        linkTextView = findViewById(R.id.linkTextView);
        noOfQuestionTV = findViewById(R.id.noOfQues);
        anyChartView = findViewById(R.id.chart);
        chart = findViewById(R.id.pieChartLayout);
        quiz = findViewById(R.id.quizLinearLayout);

        Intent intent = getIntent();
        String difficulty = intent.getStringExtra("difficulty");



        // Getting questions here
        DatabaseReference ref = FirebaseDatabase.getInstance("https://traviamania-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("questions/"+difficulty);
        Query getQues = ref.orderByChild("difficulty");
        getQues.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap: snapshot.getChildren()) {
                    Questionaire question = snap.getValue(Questionaire.class);
                    if(question != null) {
                        question.formatQ();
                        questionaireArrayList.add(question);

                    }
                }
                googleSearch = "https://en.wikipedia.org/w/index.php?title=Special:Search&search="+questionaireArrayList.get(0).getRightAnswer().replaceAll("\\s+","+");
                // start code here

                optionList.add(difficulty);
                optionListAdapter = new customOptionList(quizActivity.this, optionList);
                options.setAdapter(optionListAdapter);

                floatingButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), google_activity.class);
                        intent.putExtra("uri", linkTextView.getText().toString());
                        startActivity(intent);
                    }
                });


                options.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        googleSearch = "https://en.wikipedia.org/w/index.php?title=Special:Search&search="+questionaireArrayList.get(questions_attempted+1).getRightAnswer().replaceAll("\\s+","+");
                        linkTextView.setText(googleSearch);
                        if(questionaireArrayList.get(questions_attempted).checkRightAns(optionList.get(i))) {
                            score_of10+=1;

                            Toast.makeText(getApplicationContext(), "Correct Answer", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "Wrong Answer", Toast.LENGTH_SHORT).show();
                        }
                        questions_attempted+=1;
                        noOfQuestionTV.setText(Integer.toString(questions_attempted+1)+"/10");
                        assignQuestions(questions_attempted, questionaireArrayList);
                        if(questions_attempted == 10){
                            FirebaseAuth mAuth = FirebaseAuth.getInstance();
                            if (mAuth.getCurrentUser() != null){
                                String EMAIL = mAuth.getCurrentUser().getEmail();

                                DatabaseReference ref = FirebaseDatabase.getInstance("https://traviamania-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");
                                Query checkUser = ref.orderByChild("email").equalTo(EMAIL);
                                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.exists()) {
                                            String userEmail = mAuth.getCurrentUser().getEmail();;
                                            UserScoreClass user = snapshot.child(userEmail.split("@")[0].replace('.','_')).getValue(UserScoreClass.class);


                                            user.setNoOfQues(Integer.toString(Integer.parseInt(user.getNoOfQues())+questions_attempted));
                                            user.setScore(Integer.toString(Integer.parseInt(user.getScore())+score_of10));
                                            DatabaseReference reference = FirebaseDatabase.getInstance("https://traviamania-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");
                                            reference.child(user.getEmail().split("@")[0].replace('.','_')).setValue(user);

                                            Pie pie = AnyChart.pie();
                                            String [] wrong = {"right", "wrong", "skip"};
                                            List<DataEntry> dataEntries = new ArrayList<>();
                                            quiz.setVisibility(View.GONE);
                                            chart.setVisibility(View.VISIBLE);
                                            noOfQuestionTV.setVisibility(View.GONE);
                                            dataEntries.add(new ValueDataEntry(wrong[0], score_of10));
                                            dataEntries.add(new ValueDataEntry(wrong[1], 10-score_of10));
                                            dataEntries.add(new ValueDataEntry(wrong[2], 0));
                                            pie.data(dataEntries);
                                            anyChartView.setChart(pie);
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Log.i("FAILED", "WHAT THE HELL");
                                    }
                                });

                            }

                        }
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