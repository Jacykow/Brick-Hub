package com.gulij.brickhub.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gulij.brickhub.R
import com.gulij.brickhub.utility.DBManager

class BrickListAdapter(
    private val bricks: ArrayList<Int>
) : RecyclerView.Adapter<BrickListAdapter.BrickViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BrickViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_brick, parent, false) as LinearLayout
        return BrickViewHolder(layout)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BrickViewHolder, position: Int) {
        DBManager.getPart(bricks[position]){
            holder.layout.findViewById<TextView>(R.id.descriptionText).text = "${it.getString(0)}\n${it.getString(1)} [${it.getInt(2)}]"
            holder.layout.findViewById<TextView>(R.id.brickAmountText).text = "${it.getString(3)} of ${it.getString(4)}"
        }
    }

    override fun getItemCount() = bricks.size

    class BrickViewHolder(val layout: LinearLayout) : RecyclerView.ViewHolder(layout)
}