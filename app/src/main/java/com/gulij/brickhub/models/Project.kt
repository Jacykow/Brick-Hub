package com.gulij.brickhub.models

data class Project(val id: Int, var name: String, var bricks: ArrayList<Brick>, var lastAccessed: Int)