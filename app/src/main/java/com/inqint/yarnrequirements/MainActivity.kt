package com.inqint.yarnrequirements

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //private InfoFragment infoFragment;
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_weights -> {
                replaceFragment(WeightFragment.newInstance(), R.id.fragment_container)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_info -> {
                replaceFragment(InfoFragment.newInstance(), R.id.fragment_container)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
           addFragment(InfoFragment.newInstance(), R.id.fragment_container)
        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
