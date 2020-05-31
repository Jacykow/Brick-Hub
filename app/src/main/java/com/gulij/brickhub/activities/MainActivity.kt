package com.gulij.brickhub.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gulij.brickhub.R
import com.gulij.brickhub.adapters.ProjectListAdapter
import com.gulij.brickhub.utility.DBManager
import com.gulij.brickhub.utility.StateManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DBManager.init(this)

        projectList.setHasFixedSize(false)
        projectList.layoutManager = LinearLayoutManager(this)
        projectList.adapter = ProjectListAdapter {
            StateManager.activeProject = it
            startActivity(Intent(this, ProjectActivity::class.java))
        }

        addProjectButton.setOnClickListener {
            startActivity(Intent(this, CreateProjectActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settings) {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        (projectList.adapter as ProjectListAdapter).projects = DBManager.getProjectIds()
        projectList.adapter!!.notifyDataSetChanged()
    }
}
