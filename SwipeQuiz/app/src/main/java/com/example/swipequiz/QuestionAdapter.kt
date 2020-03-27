package com.example.swipequiz

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.question_list_item.view.*

class QuestionAdapter(val items : ArrayList<String>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    private var removedPosition: Int = 0
    private var removedItem: String = ""

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.question_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvQuestionType.text = items.get(position)
    }
    // function for removing the item from the recyclerview
    fun removeItem(position: Int, viewHolder: RecyclerView.ViewHolder) {
        removedItem = items[position]
        removedPosition = position

        items.removeAt(position)
        notifyItemRemoved(position)
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val tvQuestionType = view.tv_question_type
}