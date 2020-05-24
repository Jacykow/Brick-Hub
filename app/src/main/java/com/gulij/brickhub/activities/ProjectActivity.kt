package com.gulij.brickhub.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.gulij.brickhub.R
import com.gulij.brickhub.models.Inventory
import com.gulij.brickhub.utility.downloadXMLObject


class ProjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project)

        downloadXMLObject(
            this,
            "http://fcds.cs.put.poznan.pl/MyWeb/BL/10179.xml",
            (Inventory)::fromXMLString
        ) {
            Log.d("TAG","SUCCESS!")
        }
    }
}
