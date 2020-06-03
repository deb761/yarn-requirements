package com.inqint.yarnrequirements

import android.content.res.Resources
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.inqint.yarnrequirements.weights.WeightContent.WeightItem
import java.util.*


/**
 * [RecyclerView.Adapter] that can display a [WeightItem].
 */
class WeightRecyclerViewAdapter(private val mValues: List<WeightItem>) :

    RecyclerView.Adapter<WeightRecyclerViewAdapter.ViewHolder>() {

    val TAG:String = "WeightRecyclerVwAdptr"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_weight, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var yarn = mValues[position]
        holder.mItem = yarn!!
        var nameId = holder.mNameView.resources.getIdentifier(yarn.name + "_yarn", "string", holder.mView.context.packageName)
        holder.mNameView.text = holder.mNameView.resources.getText(nameId)
        var sizes = ""

        val country = Locale.getDefault()
        val needles = yarn.needles
        // If the user is in the US, show US needle sizes first
        if (country.equals(Locale.US)) {
            if (needles.size == 2) {
                sizes = String.format("%1\$s-%2\$s US, ", needles[0]!!.getus(), needles[1]!!.getus())
            } else {
                sizes = String.format("%s+ US, ", needles[0]!!.getus())
            }
        }
        val res = holder.mView.resources
        val mmUnits = res.getString(R.string.mm_unit)
        // Always show international needle sizes
        if (yarn.needles.size == 2) {
            sizes += String.format(res.getString(R.string.unit_range_format), needles[0]!!.getmm(), needles[1]!!.getmm(), mmUnits)
        } else {
            sizes += String.format(res.getString(R.string.unit_format), needles[0]!!.getmm(), mmUnits)
        }

        holder.mNeedleView.setText("${res.getString(R.string.needle_size)}: $sizes")

        // Show the gauge
        val gauges = yarn.gauge
        val gaugeUnits = res.getString(R.string.gauge_length)
        var gauge = ""
        if (gauges.size == 2) {
            gauge = String.format(res.getString(R.string.unit_range_format), gauges[0], gauges[1], gaugeUnits)
        } else {
            gauge = String.format(res.getString(R.string.unit_format), gauges[0], gaugeUnits)
        }
        holder.mGaugeView.setText("${res.getString(R.string.gauge_label)}: $gauge")

        // format windings string
        var wpi = ""
        val windings = yarn.wpi
        val wpiUnits = res.getString(R.string.wpi)
        if (windings.size == 2) {
            wpi = String.format(res.getString(R.string.unit_range_format), windings[0], windings[1], wpiUnits)
        } else {
            wpi = String.format(res.getString(R.string.unit_format), windings[0], wpiUnits)
        }

        val length = yarn.length.toDouble()
        val weight = yarn.weight.toDouble()

        holder.mWindingsView.text = wpi
        holder.mDensityView.text = String.format(res.getString(R.string.density_format), length, weight)
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
