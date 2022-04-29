package com.example.triviamania;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class add_question extends AppCompatActivity {

    FirebaseAuth mAuth;
    Spinner spinner;
    TextView opta, optb, optc, optd, ques;
    Button but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        spinner = (Spinner) findViewById(R.id.difficultySpinner);
        opta = findViewById(R.id.optionA);
        optb = findViewById(R.id.optionB);
        optc = findViewById(R.id.optionC);
        optd = findViewById(R.id.optionD);
        ques = findViewById(R.id.questionEV);
        but = findViewById(R.id.addQues);

        mAuth = FirebaseAuth.getInstance();



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.difficulty_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        String difficulty = spinner.getSelectedItem().toString().toLowerCase();
        DatabaseReference ref = FirebaseDatabase.getInstance("https://traviamania-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("questions/"+difficulty);

//

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAuth.getCurrentUser() != null){
                    final String difficulty = spinner.getSelectedItem().toString().toLowerCase();
                    String EMAIL= mAuth.getCurrentUser().getEmail();
                    String opa = opta.getText().toString();
                    String opb = optb.getText().toString();
                    String opc = optc.getText().toString();
                    String opd = optd.getText().toString();
                    String qu = ques.getText().toString();
                    Questionaire question = new Questionaire(EMAIL, difficulty, qu, opa, opb, opc, opd);
                    ref.push().setValue(question);
                    Toast.makeText(getApplicationContext(), "Question Posted", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
}