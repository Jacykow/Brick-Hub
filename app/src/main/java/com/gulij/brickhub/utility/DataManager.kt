package com.gulij.brickhub.utility

import android.content.Context
import com.gulij.brickhub.models.Project

object DataManager {
    lateinit var projects: HashMap<Int, Project>

    fun addProject(project: Project) {
        projects[project.id] = project
    }

    fun init(context: Context) {
        if (this::projects.isInitialized) {
            return
        }

        DBManager.init(context)

        projects = HashMap()
    }
}