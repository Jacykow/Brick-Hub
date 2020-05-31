package com.gulij.brickhub.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gulij.brickhub.R
import com.gulij.brickhub.models.Inventory
import com.gulij.brickhub.utility.DBManager
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
                    DBManager.addProject(projectNameEditText.text.toString()) {
                        StateManager.activeProject = it.getInt(0)

                        for (item in inventory.filter { item -> item.alternate == "N" }) {
                            DBManager.addPartFromItem(item, StateManager.activeProject!!) {}
                        }

                        startActivity(Intent(this, ProjectActivity::class.java))
                    }
                }
            }
        }
    }
}
