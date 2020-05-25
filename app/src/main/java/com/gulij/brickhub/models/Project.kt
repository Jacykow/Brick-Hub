package com.gulij.brickhub.models

import com.gulij.brickhub.utility.DBManager.getBrickByItemId

data class Project(val id: Int, var name: String, var bricks: ArrayList<Brick>, var lastAccess: Int) {
}