package com.gulij.brickhub.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gulij.brickhub.R
import com.gulij.brickhub.adapters.BrickListAdapter
import com.gulij.brickhub.utility.DBManager
import com.gulij.brickhub.utility.StateManager
import kotlinx.android.synthetic.main.activity_project.*

class ProjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project)

        brickList.setHasFixedSize(false)
        brickList.layoutManager = LinearLayoutManager(this)
        DBManager.updateProject(StateManager.activeProject!!)
        brickList.adapter = BrickListAdapter(DBManager.getPartIds(StateManager.activeProject!!))
    }

    override fun onResume() {
        super.onResume()
    }
}
