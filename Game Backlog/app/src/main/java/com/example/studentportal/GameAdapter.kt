package com.example.studentportal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studentportal.model.Backlog
import kotlinx.android.synthetic.main.game_item.view.*


class GameAdapter():
    RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.game_item, parent, false)
        )
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(game : Backlog) {
            itemView.tvTitle.text = game.backlogTitle
            itemView.tvPlaform.text = game.platform
            itemView.tvReleaseDate.text = "Release: " + game.releaseDate
        }
    }

    companion object {
        var games = arrayListOf<Backlog>()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(games[position])
    }

    override fun getItemCount(): Int = games.size

    fun setGames(gameBacklog : List<Backlog>) {
        games.clear()
        games.addAll(gameBacklog)
    }
}