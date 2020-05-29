package com.gulij.brickhub.utility

import com.gulij.brickhub.models.Project

object StateManager {
    var activeProject: Project? = null
        set(value) {
            value?.lastAccessed = (System.currentTimeMillis() / 1000).toInt()
            field = value
        }
}