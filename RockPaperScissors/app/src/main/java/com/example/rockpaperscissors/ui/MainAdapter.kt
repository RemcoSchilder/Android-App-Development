package com.example.rockpaperscissors.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.model.Play
import kotlinx.android.synthetic.main.item_history.view.*

class MainAdapter() : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    companion object {
        var playHistoryList = arrayListOf<Play>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        )
    }

    fun setHistory(playHistory : List<Play>) {
        playHistoryList.clear()
        playHistoryList.addAll(playHistory)
    }

    override fun getItemCount(): Int = playHistoryList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(playHistoryList[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(play: Play) {
            var historyTitleText: String
            var historyPlayerChoice: Int
            var historyPCChoice: Int

            if(play.result == 0){ historyTitleText = "You win!" }
            else if (play.result == 1) { historyTitleText = "Draw!" }
            else { historyTitleText = "You lose!" }

            if(play.playerChoice == 0){ historyPlayerChoice = R.drawable.rock }
            else if (play.playerChoice == 1) { historyPlayerChoice = R.drawable.paper }
            else { historyPlayerChoice = R.drawable.scissors }

            if(play.computerChoice == 0){ historyPCChoice = R.drawable.rock }
            else if (play.computerChoice == 1) { historyPCChoice = R.drawable.paper }
            else { historyPCChoice = R.drawable.scissors }

            itemView.historyTitle.text = historyTitleText
            itemView.historyDate.text = play.date.toString()
            itemView.ivHistoryPlayer.setImageResource(historyPlayerChoice)
            itemView.ivHistoryPC.setImageResource(historyPCChoice)
        }
    }
}