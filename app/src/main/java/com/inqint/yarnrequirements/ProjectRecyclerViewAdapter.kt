package com.inqint.yarnrequirements

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.inqint.yarnrequirements.ProjectListFragment.OnListFragmentInteractionListener
import com.inqint.yarnrequirements.Projects.Project

/**
 * [RecyclerView.Adapter] that can display a [Project] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class ProjectRecyclerViewAdapter(
    private val mValues: List<Project>,
    private val mListener: OnListFragmentInteractionListener?
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
            mListener?.onListFragmentInteraction(holder.mItem!!)
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
