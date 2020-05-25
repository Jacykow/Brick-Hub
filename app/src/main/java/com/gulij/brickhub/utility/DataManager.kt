package com.gulij.brickhub.utility

import android.R.attr.x
import android.content.Context
import com.gulij.brickhub.models.Project
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


object DataManager {
    lateinit var projects: HashMap<Int, Project>
    lateinit var projectList: ArrayList<Project>

    fun addProject(project: Project) {
        projects[project.id] = project
        projectList.add(project)
    }

    fun init(context: Context) {
        if (this::projects.isInitialized) {
            return
        }

        DBManager.init(context)

        projects = HashMap()
    }
}