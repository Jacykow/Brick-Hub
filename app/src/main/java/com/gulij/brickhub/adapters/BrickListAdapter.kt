package com.gulij.brickhub.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gulij.brickhub.R
import com.gulij.brickhub.models.Brick
import com.gulij.brickhub.utility.DBManager

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
        val brick = bricks[position]
        val nameAndColor = DBManager.getBrickNameAndColor(brick)
        holder.layout.findViewById<TextView>(R.id.descriptionText).text = "${nameAndColor.first}\n${nameAndColor.second} [${brick.itemId}]"
        holder.layout.findViewById<TextView>(R.id.brickAmountText).text = "${brick.quantityInStore} of ${brick.quantityInSet}"
    }

    override fun getItemCount() = bricks.size

    class BrickViewHolder(val layout: LinearLayout) : RecyclerView.ViewHolder(layout)
}