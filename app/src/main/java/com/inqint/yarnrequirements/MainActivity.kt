package com.inqint.yarnrequirements

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

open class MainActivity() : AppCompatActivity() {

    private var phoneDevice = true // used to force portrait mode

    protected val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val navController = findNavController(R.id.fragment_container)
        when (item.itemId) {
            R.id.navigation_home -> {
                navController.navigate(R.id.action_global_list)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_weights -> {
                navController.navigate(R.id.action_global_weights)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_info -> {
                navController.navigate(R.id.action_global_info)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    constructor(parcel: Parcel) : this() {
        phoneDevice = parcel.readByte() != 0.toByte()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findNavController(R.id.fragment_container).navigate(R.id.action_global_list)

        // determine screen size
        val screenSize = resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK

        // if device is a tablet, set phoneDevice to false
        if (screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE || screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE)
            phoneDevice = false // not a phone-sized device

        // if running on phone-sized device, allow only portrait orientation
        if (phoneDevice)
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    companion object CREATOR : Parcelable.Creator<MainActivity> {
        override fun createFromParcel(parcel: Parcel): MainActivity {
            return MainActivity(parcel)
        }

        override fun newArray(size: Int): Array<MainActivity?> {
            return arrayOfNulls(size)
        }
    }

}
