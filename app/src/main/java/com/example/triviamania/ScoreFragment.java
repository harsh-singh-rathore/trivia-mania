package com.example.triviamania;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScoreFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ScoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScoreFragment newInstance(String param1, String param2) {
        ScoreFragment fragment = new ScoreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    private ScoreCardAdapter listAdapter;
    private ArrayList<ScoreCard> scoreCardArrayList = new ArrayList<>();
    private RecyclerView recycler;
    AnyChartView anyChartView;

    private void updateScores() {
        DatabaseReference ref = FirebaseDatabase.getInstance("https://traviamania-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");
        Query getScoreQuery = ref.orderByChild("score");
        getScoreQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap: snapshot.getChildren()) {
                    UserScoreClass user = snap.getValue(UserScoreClass.class);
                    String userName = user.getEmail().split("@")[0].replace('.', '_');
                    String score = "0";
                    Log.d("Score", "onDataChange: " + score);
                    try {
                        if (Integer.parseInt(user.getNoOfQues()) != 0) {
                            score = Float.toString(100 * Integer.parseInt(user.getScore()) / (float) Integer.parseInt(user.getNoOfQues()));
                        }
                        scoreCardArrayList.add(new ScoreCard(userName, score));

                        Log.d("what", "onDataChange: "+userName);
                    }catch ( Exception e) {
                        Log.d("Lmao exception", "onDataChange: "+e.toString());
                    }
                    Collections.sort(scoreCardArrayList, new Comparator<ScoreCard>() {
                        @Override
                        public int compare(ScoreCard scoreCard, ScoreCard t1) {
                            return -1* scoreCard.getScore().compareTo(t1.getScore());
                        }
                    });
                    listAdapter.notifyDataSetChanged();

                }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    void setBarGraph(String email) {
        DatabaseReference ref = FirebaseDatabase.getInstance("https://traviamania-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("prev_score/"+email.split("@")[0].replace('.','_'));
        Query getScoreQuery = ref.orderByKey();
        getScoreQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Cartesian cartesian = AnyChart.column();
                List<DataEntry> data = new ArrayList<>();
                List<PrevScore> prevScoreList = new ArrayList<>();
                for(DataSnapshot snap: snapshot.getChildren()) {
                    PrevScore prevScore = snap.getValue(PrevScore.class);
                    prevScoreList.add(prevScore);
                }

                int n = prevScoreList.size() > 5? prevScoreList.size()-5 : 0;
                for(int i=n; i<prevScoreList.size(); i++) {
                    data.add(new ValueDataEntry(Integer.toString(i-n+1), Integer.parseInt(prevScoreList.get(i).getRight())));
                }

                Column column = cartesian.column(data);

                column.tooltip()
                        .titleFormat("{%X}")
                        .position(Position.CENTER_BOTTOM)
                        .anchor(Anchor.CENTER_BOTTOM)
                        .offsetX(0d)
                        .offsetY(5d)
                        .format("${%Value}{groupsSeparator: }");

                cartesian.animation(true);
                cartesian.title("Score of the last 5 attempts");

                cartesian.yScale().minimum(0d);

                cartesian.yAxis(0).labels();

                cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
                cartesian.interactivity().hoverMode(HoverMode.BY_X);

                cartesian.xAxis(0).title("Attempt");
                cartesian.yAxis(0).title("Score");

                anyChartView.setChart(cartesian);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_score, container, false);

        recycler = v.findViewById(R.id.recycler_view);
        anyChartView = v.findViewById(R.id.barGraph);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            String email = mAuth.getCurrentUser().getEmail();
            setBarGraph(email);
        }


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(layoutManager);

        listAdapter = new ScoreCardAdapter(getContext(), scoreCardArrayList);
        recycler.setAdapter(listAdapter);

        //Load the date from the network or other resources
        //into the array list asynchronously

//        scoreCardArrayList.add(new ScoreCard("Daniel Shiffman", "10"));
//        scoreCardArrayList.add(new ScoreCard("Jhon Doe", "20"));
//        scoreCardArrayList.add(new ScoreCard("Jane Doe", "30"));
        updateScores();


        return v;
    }
}