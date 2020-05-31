package com.gulij.brickhub.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gulij.brickhub.R
import com.gulij.brickhub.utility.DBManager

class ProjectListAdapter(
    private val selectedProjectListener: (Int) -> Unit
) :
    RecyclerView.Adapter<ProjectListAdapter.ProjectViewHolder>() {

    var projects: ArrayList<Int> = arrayListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProjectViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_project, parent, false) as TextView
        return ProjectViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        DBManager.getProject(projects[position]) {
            holder.textView.text = it.getString(0)
        }
        holder.textView.setOnClickListener {
            selectedProjectListener.invoke(projects[holder.adapterPosition])
        }
    }

    override fun getItemCount() = projects.size

    class ProjectViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
}