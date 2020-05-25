package com.gulij.brickhub.models

data class Project(var name: String, var bricks: ArrayList<Brick>) {
    constructor(name: String, inventory: Inventory) : this(
        name,
        ArrayList<Brick>(inventory.map { Brick(it) })
    )
}