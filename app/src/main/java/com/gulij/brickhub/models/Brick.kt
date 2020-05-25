package com.gulij.brickhub.models

data class Brick(private val name: String) {
    constructor(item: Item) : this(item.itemId ?: "no name")
}