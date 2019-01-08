package com.inqint.yarnrequirements

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.io.IOException
import java.nio.charset.Charset

class WeightFragment : Fragment() {
    companion object {

        fun newInstance(): WeightFragment {
            return WeightFragment()
        }
    }

    var weightContent : WeightContent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var json = ""
        try {
            val `is` = activity!!.assets.open("weights.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer, Charset.forName("UTF-8"))
        } catch (e: IOException) {
            e.printStackTrace()
        }

        weightContent = WeightContent(json)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return inflater.inflate(R.layout.fragment_weight, container, false)

        val view = inflater.inflate(R.layout.fragment_weight_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            val context = view.getContext()
            val recyclerView = view as RecyclerView
            recyclerView.layoutManager = LinearLayoutManager(context)
            val mDividerItemDecoration = DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).getOrientation()
            )
            recyclerView.addItemDecoration(mDividerItemDecoration)
            recyclerView.adapter = WeightRecyclerViewAdapter(WeightContent.ITEMS)
        }
        return view

    }
}