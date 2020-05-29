package com.gulij.brickhub.utility

import android.content.Context
import com.gulij.brickhub.models.Project


object DataManager {
    lateinit var projects: ArrayList<Project>

    fun addProject(project: Project) {
        projects.add(project)
    }

    fun updateProject(project: Project) {

    }

    fun init(context: Context) {
        if (this::projects.isInitialized) {
            return
        }

        DBManager.init(context)
        projects = ArrayList()
    }
}