package com.example.notepadkotlin.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.notepadkotlin.R
import kotlinx.android.synthetic.main.edit_activity.*
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_activity.toolbar
import java.util.*

class EditActivity : AppCompatActivity() {

    private lateinit var editActivityViewModel: EditActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_activity)
        setSupportActionBar(toolbar)

        initViews()
        initViewModel()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun initViews() {
        // Setup action bar with title and back button
        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.app_name)
        actionbar.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)

        saveNotepadFab.setOnClickListener {
            editActivityViewModel.note.value?.apply {
                title = editTextTitle.text.toString()
                lastUpdated = Date()
                text = editTextNote.text.toString()
            }

            editActivityViewModel.updateNote()
        }
    }

    private fun initViewModel() {
        editActivityViewModel = ViewModelProvider(this).get(EditActivityViewModel::class.java)
        editActivityViewModel.note.value = intent.extras?.getParcelable(EXTRA_NOTE)!!

        editActivityViewModel.note.observe(this, Observer { note ->
            if (note != null) {
                editTextTitle.setText(note.title)
                editTextNote.setText(note.text)
            }
        })

        editActivityViewModel.error.observe(this, Observer { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })

        editActivityViewModel.success.observe(this, Observer { success ->
            if (success) finish()
        })
    }

    companion object {
        const val EXTRA_NOTE = "EXTRA_NOTE"
    }

}
