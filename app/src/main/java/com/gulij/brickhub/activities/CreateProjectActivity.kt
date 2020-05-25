package com.gulij.brickhub.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gulij.brickhub.R
import com.gulij.brickhub.models.Inventory
import com.gulij.brickhub.models.Project
import com.gulij.brickhub.utility.DBManager
import com.gulij.brickhub.utility.DataManager
import com.gulij.brickhub.utility.StateManager
import com.gulij.brickhub.utility.downloadXMLObject
import kotlinx.android.synthetic.main.activity_create_project.*

class CreateProjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_project)

        confirmButton.setOnClickListener {
            confirmButton.isEnabled = false

            downloadXMLObject(
                this,
                "http://fcds.cs.put.poznan.pl/MyWeb/BL/" + setNumberEditText.text.toString() + ".xml",
                (Inventory)::fromXMLString
            ) { inventory ->
                if (inventory == null) {
                    Toast.makeText(this, "Invalid Set Number!", Toast.LENGTH_SHORT).show()
                    confirmButton.isEnabled = true
                } else {
                    val createdProject = Project(
                        (DataManager.projects.keys.max() ?: 0) + 1,
                        projectNameEditText.text.toString(),
                        ArrayList(inventory.filter { it.alternate == "N" }.mapNotNull {
                            DBManager.getBrickByItemId(
                                it.itemId!!
                            )
                        }),
                        -1
                    )


                    StateManager.activeProject = createdProject
                    DataManager.addProject(createdProject)
                    startActivity(Intent(this, ProjectActivity::class.java))
                }
            }
        }
    }
}
