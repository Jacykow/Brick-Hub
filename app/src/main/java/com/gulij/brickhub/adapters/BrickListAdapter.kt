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
    var bricks: ArrayList<Int>
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
        val partValues = DBManager.getPart(bricks[position])!!
        holder.layout.findViewById<TextView>(R.id.descriptionText).text =
            "${partValues["name"]}\n${partValues["color"]} [${partValues["code"]}]"
        val partAmount = DBManager.getPartAmount(bricks[position])!!.values.toList()
        holder.layout.findViewById<TextView>(R.id.brickAmountText).text =
            "${partAmount[0]} of ${partAmount[1]}"

        val partId = bricks[position]
        holder.layout.findViewById<TextView>(R.id.buttonMinus).setOnClickListener {
            val partAmounts = DBManager.changePartAmount(partId, -1)!!.values.toList()
            holder.layout.findViewById<TextView>(R.id.brickAmountText).text =
                "${partAmounts[0]} of ${partAmounts[1]}"
        }
        holder.layout.findViewById<TextView>(R.id.buttonPlus).setOnClickListener {
            val partAmounts = DBManager.changePartAmount(partId, 1)!!.values.toList()
            holder.layout.findViewById<TextView>(R.id.brickAmountText).text =
                "${partAmounts[0]} of ${partAmounts[1]}"
        }
    }

    override fun getItemCount() = bricks.size

    class BrickViewHolder(val layout: LinearLayout) : RecyclerView.ViewHolder(layout)
}