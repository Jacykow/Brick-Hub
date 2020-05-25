package com.gulij.brickhub.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gulij.brickhub.R
import com.gulij.brickhub.adapters.BrickListAdapter
import com.gulij.brickhub.models.Inventory
import com.gulij.brickhub.utility.StateManager
import com.gulij.brickhub.utility.downloadXMLObject
import kotlinx.android.synthetic.main.activity_project.*


class ProjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project)

        brickList.setHasFixedSize(false)
        brickList.layoutManager = LinearLayoutManager(this)
        brickList.adapter = BrickListAdapter(StateManager.activeProject!!.bricks)
    }
}
