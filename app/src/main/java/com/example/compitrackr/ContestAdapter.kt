package com.example.compitrackr

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContestAdapter(private val contestList: List<Contest>) : RecyclerView.Adapter<ContestAdapter.ContestViewHolder>() {

    class ContestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contestName: TextView = itemView.findViewById(R.id.contestName)
        val contestRank: TextView = itemView.findViewById(R.id.contestRank)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contest_item, parent, false)
        return ContestViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContestViewHolder, position: Int) {
        val contest = contestList[position]
        holder.contestName.text = contest.name
        holder.contestRank.text = "Rank: ${contest.rank}"
    }

    override fun getItemCount(): Int = contestList.size
}
