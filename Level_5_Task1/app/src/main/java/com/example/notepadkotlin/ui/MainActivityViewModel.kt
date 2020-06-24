package com.example.notepadkotlin.ui

import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import com.example.notepadkotlin.R
import com.example.notepadkotlin.database.NoteRepository
import kotlinx.android.synthetic.main.main_activity.*

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val noteRepository = NoteRepository(application.applicationContext)

    val note = noteRepository.getNotepad()

}
