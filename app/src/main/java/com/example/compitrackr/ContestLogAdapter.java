package com.example.compitrackr;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContestLogAdapter extends RecyclerView.Adapter<ContestLogAdapter.ContestLogViewHolder> {

    private List<ContestLog> contestLogs;

    public ContestLogAdapter(List<ContestLog> contestLogs) {
        this.contestLogs = contestLogs;
    }

    @Override
    public ContestLogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ContestLogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContestLogViewHolder holder, int position) {
        ContestLog log = contestLogs.get(position);
        String logInfo = "Platform: " + log.getPlatform() + "\n" +
                "Contest: " + log.getContestName() + "\n" +
                "Date: " + log.getDate().toString() + "\n" +
                "Rank: " + log.getRank() + "\n" +
                "Old Rating: " + log.getOldRating() + "\n" +
                "New Rating: " + log.getNewRating() + "\n" +
                "Problems Solved: " + log.getProblemsSolvedCount();
        holder.textViewLogInfo.setText(logInfo);
    }

    @Override
    public int getItemCount() {
        return contestLogs.size();
    }

    public static class ContestLogViewHolder extends RecyclerView.ViewHolder {

        TextView textViewLogInfo;

        public ContestLogViewHolder(View itemView) {
            super(itemView);
            textViewLogInfo = itemView.findViewById(android.R.id.text1);
        }
    }
}
