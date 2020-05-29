package com.gulij.brickhub.utility

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.gulij.brickhub.models.Project

object DataManager {
    lateinit var projects: ArrayList<Project>
    lateinit var brickImages: HashMap<Int, Bitmap?>

    fun loadImage(brickId: Int, imageView: ImageView) {
        if (brickImages.containsKey(brickId)) {
            if (brickImages[brickId] != null) {
                imageView.setImageBitmap(brickImages[brickId])
            } else {

            }
        } else {

        }
    }

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
        brickImages = HashMap()
    }
}