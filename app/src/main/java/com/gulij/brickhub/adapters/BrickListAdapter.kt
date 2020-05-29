package com.gulij.brickhub.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gulij.brickhub.R
import com.gulij.brickhub.models.Brick

class BrickListAdapter(
    private val bricks: ArrayList<Brick>
) : RecyclerView.Adapter<BrickListAdapter.BrickViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BrickViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_brick, parent, false) as LinearLayout
        return BrickViewHolder(layout)
    }

    override fun onBindViewHolder(holder: BrickViewHolder, position: Int) {
        holder.layout.findViewById<TextView>(R.id.descriptionText).text = bricks[position].id.toString()
    }

    override fun getItemCount() = bricks.size

    class BrickViewHolder(val layout: LinearLayout) : RecyclerView.ViewHolder(layout)
}