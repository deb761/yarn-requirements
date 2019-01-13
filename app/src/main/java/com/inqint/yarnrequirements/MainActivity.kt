package com.inqint.yarnrequirements

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.inqint.yarnrequirements.Projects.Project
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity() : AppCompatActivity(),
    ProjectListFragment.OnListFragmentInteractionListener,
    ProjectFragment.OnFragmentInteractionListener{

    private lateinit var listFragment: ProjectListFragment
    private lateinit var projectFragment: ProjectFragment
    private lateinit var weightFragment: WeightFragment
    private lateinit var infoFragment: InfoFragment
    private var projectPage = false
    private var phoneDevice = true // used to force portrait mode

    //private InfoFragment infoFragment;
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                replaceFragment(listFragment, R.id.fragment_container)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_weights -> {
                replaceFragment(weightFragment, R.id.fragment_container)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_info -> {
                replaceFragment(infoFragment, R.id.fragment_container)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    constructor(parcel: Parcel) : this() {
        phoneDevice = parcel.readByte() != 0.toByte()
    }

    fun createFragments() {
        listFragment = ProjectListFragment.newInstance()
        weightFragment = WeightFragment.newInstance()
        infoFragment = InfoFragment.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createFragments()

        // determine screen size
        val screenSize = resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK

        // if device is a tablet, set phoneDevice to false
        if (screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE || screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE)
            phoneDevice = false // not a phone-sized device

        // if running on phone-sized device, allow only portrait orientation
        if (phoneDevice)
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        if (savedInstanceState == null) {
            addFragment(listFragment, R.id.fragment_container)
        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
/*
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (phoneDevice) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }
*/
    companion object CREATOR : Parcelable.Creator<MainActivity> {
        override fun createFromParcel(parcel: Parcel): MainActivity {
            return MainActivity(parcel)
        }

        override fun newArray(size: Int): Array<MainActivity?> {
            return arrayOfNulls(size)
        }
    }

    /*
     * When a project is selected, open it's page
     */
    override fun onListFragmentInteraction(project: Project) {
        try {
            projectFragment = project.fragment.newInstance() as ProjectFragment
            projectFragment.project = project
        } catch (e: Exception) {
            println(e)
        }

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, projectFragment)
            .addToBackStack(null).commit()
        projectPage = true
    }


    override fun onFragmentInteraction(uri: Uri) {

    }

}
