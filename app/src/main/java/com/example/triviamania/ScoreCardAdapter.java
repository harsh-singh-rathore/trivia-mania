package com.example.triviamania;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScoreCardAdapter extends RecyclerView.Adapter<ScoreCardAdapter.ScoreCardViewHolder> {
    private ArrayList<ScoreCard> mScoreCard = new ArrayList<>();
    private Context mContext;

    public ScoreCardAdapter(Context context, ArrayList<ScoreCard> scoreCard) {
        mContext = context;
        mScoreCard = scoreCard;
    }

    @Override
    public ScoreCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.scoreboard_list_view, parent, false);
        return new ScoreCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreCardViewHolder holder, final int position) {
        final ScoreCard scoreCard = mScoreCard.get(position);

        // Set the data to the views here
        holder.setUserName(scoreCard.getUserName());
        holder.setUserScore(scoreCard.getScore());

        // You can set click listners to indvidual items in the viewholder here
        // make sure you pass down the listner or make the Data members of the viewHolder public

    }

    @Override
    public int getItemCount() {
        return mScoreCard == null? 0: mScoreCard.size();
    }

    public class ScoreCardViewHolder extends RecyclerView.ViewHolder {
        private TextView userName;
        private TextView userScore;

        public ScoreCardViewHolder(View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.scoreUserName);
            userScore = itemView.findViewById(R.id.scoreUserScore);

        }

        public void setUserName(String usrName) {
            userName.setText(usrName);
        }

        public void setUserScore(String usrScore) {
            userScore.setText(usrScore);
        }

    };
}
