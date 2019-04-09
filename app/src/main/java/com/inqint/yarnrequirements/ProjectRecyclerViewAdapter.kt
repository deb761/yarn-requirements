package com.inqint.yarnrequirements

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.inqint.yarnrequirements.Projects.DimensionProject
import com.inqint.yarnrequirements.Projects.Project
import com.inqint.yarnrequirements.Projects.SizeProject

class ProjectRecyclerViewAdapter(
    private val mValues: List<Project>
) : RecyclerView.Adapter<ProjectRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_project_icon, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mNameView.text = holder.mItem!!.name
        holder.imageView.setImageResource(holder.mItem!!.thumbImageID)

        holder.mView.setOnClickListener {
            val project = mValues[position]
            if (project is DimensionProject) {
                val projectFragment = ProjectListFragmentDirections.actionListToDimensionProjectFragment(project)
                it.findNavController().navigate(projectFragment)
            }
            else if (project is SizeProject) {
                val projectFragment = ProjectListFragmentDirections.actionListToSizeProject(project)
                it.findNavController().navigate(projectFragment)
            }
            else {
                val projectFragment = ProjectListFragmentDirections.actionListToSockProjectFragment(project)
                it.findNavController().navigate(projectFragment)
            }
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mNameView: TextView
        val imageView: ImageView
        var mItem: Project? = null

        init {
            mNameView = mView.findViewById<View>(R.id.name) as TextView
            imageView = mView.findViewById<View>(R.id.thumb_image) as ImageView
        }

        override fun toString(): String {
            return super.toString() + " '" + mNameView.text + "'"
        }
    }

}
