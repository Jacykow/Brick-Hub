package com.gulij.brickhub.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gulij.brickhub.R
import com.gulij.brickhub.utility.DBManager
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        wipeDataButton.setOnClickListener {
            DBManager.wipeData {  }
        }
    }
}
