package com.example.studentportal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.studentportal.PortalAdapter.Companion.portals
import kotlinx.android.synthetic.main.add_portal.*

class AddNewPortal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_portal)

        initViews()
    }

    private fun initViews() {
        val actionbar = supportActionBar
        actionbar!!.title = "Create a Portal"
        actionbar.setDisplayHomeAsUpEnabled(true)

        btnAddPortal.setOnClickListener() {
            portals.add(Portal(etPortalTitle.text.toString(), etPortalURL.text.toString()))
            this.finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
