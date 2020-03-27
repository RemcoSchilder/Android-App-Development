package com.example.swipequiz

import android.content.Context
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.graphics.drawable.ColorDrawable
import android.graphics.Color
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val questions: ArrayList<String> = ArrayList()
    private lateinit var colorDrawableBackgroundRed: ColorDrawable
    private lateinit var colorDrawableBackgroundGreen: ColorDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addQuestions()

        rv_swipe_quiz.layoutManager = LinearLayoutManager(this)
        rv_swipe_quiz.adapter = QuestionAdapter(questions, this)
        rv_swipe_quiz.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        colorDrawableBackgroundRed = ColorDrawable(Color.parseColor("#ff0000"))
        colorDrawableBackgroundGreen = ColorDrawable(Color.parseColor("#008000"))

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, viewHolder2: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDirection: Int) {
                (rv_swipe_quiz.adapter as QuestionAdapter).removeItem(viewHolder.adapterPosition, viewHolder)

//                if (viewHolder.adapterPosition == 1 && swipeDirection < 0) {Toast.makeText(viewHolder.itemView.context,"Answer is correct", Toast.LENGTH_LONG).show() }
//                else { Toast.makeText(viewHolder.itemView.context,"Answer is not correct", Toast.LENGTH_LONG).show() }
//
//                if (viewHolder.adapterPosition == 2 && swipeDirection > 0) {Toast.makeText(viewHolder.itemView.context,"Answer is correct", Toast.LENGTH_LONG).show() }
//                else { Toast.makeText(viewHolder.itemView.context,"Answer is not correct", Toast.LENGTH_LONG).show() }
//
//                if (viewHolder.adapterPosition == 3 && swipeDirection > 0) {Toast.makeText(viewHolder.itemView.context,"Answer is correct", Toast.LENGTH_LONG).show() }
//                else { Toast.makeText(viewHolder.itemView.context,"Answer is not correct", Toast.LENGTH_LONG).show() }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView

                if (dX > 0) {
                    colorDrawableBackgroundGreen.setBounds(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                } else {
                    colorDrawableBackgroundRed.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                }

                colorDrawableBackgroundGreen.draw(c)
                colorDrawableBackgroundRed.draw(c)

                c.save()

                if (dX > 0)
                    c.clipRect(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                else
                    c.clipRect(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)

                c.restore()

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rv_swipe_quiz)
    }

    fun addQuestions() {
        questions.add("A 'val' and 'var' are the same")
        questions.add("Mobile Application Development grants 12 ECTS")
        questions.add("A Unit in Kotlin corresponds to a void in Java")
        questions.add("In Kotlin 'when' replaces the 'switch' operator in Java")
    }
}
