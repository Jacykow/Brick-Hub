package com.gulij.brickhub.utility

import android.content.Context
import com.gulij.brickhub.models.Brick
import com.gulij.brickhub.models.Project

object DataManager {
    lateinit var projects: ArrayList<Project>

    fun init(context: Context) {
        if(!this::projects.isInitialized){
            projects = arrayListOf(Project("Wózek", arrayListOf(Brick("część 1"),Brick("część 2"))))
        }
    }
}