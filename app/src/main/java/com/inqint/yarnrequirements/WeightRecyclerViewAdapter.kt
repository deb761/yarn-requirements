package com.inqint.yarnrequirements

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.inqint.yarnrequirements.weights.WeightContent.WeightItem
import java.util.*


/**
 * [RecyclerView.Adapter] that can display a [WeightItem].
 */
class WeightRecyclerViewAdapter(private val mValues: List<WeightItem>) :
    RecyclerView.Adapter<WeightRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_weight, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mNameView.text = mValues[position].name
        var sizes = ""

        val country = Locale.getDefault()
        val needles = holder.mItem!!.needles
        // If the user is in the US, show US needle sizes first
        if (country.equals(Locale.US)) {
            if (needles.size == 2) {
                sizes = String.format("%1\$s-%2\$s US, ", needles[0]!!.getus(), needles[1]!!.getus())
            } else {
                sizes = String.format("%s+ US, ", needles[0]!!.getus())
            }
        }
        // Always show international needle sizes
        if (holder.mItem!!.needles.size == 2) {
            sizes += String.format("%1$.2f-%2$.2f mm", needles[0]!!.getmm(), needles[1]!!.getmm())
        } else {
            sizes += String.format("%.2f mm", needles[0]!!.getmm())
        }
        val res = holder.mView.getResources()

        holder.mNeedleView.setText(res.getString(R.string.needle_size) + ": " + sizes)

        // Show the gauge
        val gauges = holder.mItem!!.gauge
        var gauge = ""
        if (gauges.size == 2) {
            gauge = String.format("%1$.0f-%2$.0f ", gauges[0], gauges[1])
        } else {
            gauge = String.format("%.0f ", gauges[0])
        }
        holder.mGaugeView.setText(res.getString(R.string.gauge) + ": " + gauge + res.getString(R.string.gauge_length))

        // format windings string
        var wpi = ""
        val windings = holder.mItem!!.wpi
        if (windings.size == 2) {
            wpi = String.format("%1$.0f-%2$.0f", windings[0], windings[1])
        } else {
            wpi = String.format("%.0f", windings[0])
        }

        val length = holder.mItem!!.length.toDouble()
        val ball = holder.mItem!!.weight.toDouble()

        holder.mWindingsView.text = String.format("%1\$s %2\$s", wpi, res.getString(R.string.wpi))
        holder.mDensityView.text = String.format(
            "%1$.0f%2\$s %3\$s %4\$s%5\$s %6\$s", length,
            res.getString(R.string.meter_abbr), res.getString(R.string.per),
            holder.mItem!!.weight, res.getString(R.string.gram_abbr),
            res.getString(R.string.yarn_ball)
        )
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mNameView: TextView
        val mNeedleView: TextView
        val mGaugeView: TextView
        val mWindingsView: TextView
        val mDensityView: TextView
        var mItem: WeightItem? = null

        init {
            mNameView = mView.findViewById(R.id.name)
            mNeedleView = mView.findViewById(R.id.needles)
            mGaugeView = mView.findViewById(R.id.gauge)
            mWindingsView = mView.findViewById(R.id.wpi)
            mDensityView = mView.findViewById(R.id.density)
        }

        override fun toString(): String {
            return super.toString() + " '" + mNeedleView.text + "'"
        }
    }
}
