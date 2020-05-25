package com.gulij.brickhub.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gulij.brickhub.R
import com.gulij.brickhub.adapters.ProjectListAdapter
import com.gulij.brickhub.utility.DBManager
import com.gulij.brickhub.utility.DataManager
import com.gulij.brickhub.utility.StateManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DataManager.init(this)
        DBManager.init(this)

        projectList.setHasFixedSize(false)
        projectList.layoutManager = LinearLayoutManager(this)
        projectList.adapter = ProjectListAdapter(DataManager.projectList) {
            StateManager.activeProject = it
            startActivity(Intent(this, ProjectActivity::class.java))
        }

        addProjectButton.setOnClickListener {
            startActivity(Intent(this, CreateProjectActivity::class.java))
        }
    }
}
