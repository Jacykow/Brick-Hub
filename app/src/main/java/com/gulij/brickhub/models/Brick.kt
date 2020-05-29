package com.gulij.brickhub.models

data class Brick(
    val id: Int,
    val inventoryId: Int,
    val typeId: Int,
    val itemId: Int,
    val quantityInSet: Int,
    val quantityInStore: Int,
    val colorId: Int
)