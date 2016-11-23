package com.inqint.yarnrequirements;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.inqint.yarnrequirements.Projects.Project;

import java.lang.reflect.Constructor;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ProjectListFragment.OnListFragmentInteractionListener,
        ProjectFragment.OnFragmentInteractionListener {

    private boolean phoneDevice = true; // used to force portrait mode
    private ProjectListFragment listFragment;
    private ProjectFragment projectFragment;
    private WeightFragment weightFragment;
    private InfoFragment infoFragment;
    private boolean projectPage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // determine screen size
        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        // if device is a tablet, set phoneDevice to false
        if (screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE ||
                screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE)
            phoneDevice = false; // not a phone-sized device

        // if running on phone-sized device, allow only portrait orientation
        if (phoneDevice)
            setRequestedOrientation(
                    ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            listFragment = new ProjectListFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            listFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, listFragment).commit();
        }
    }
    /* When the back button is press, remove the nav drawer if open,
     * or go back to the list of projects if a project is shown.
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (projectPage) {
            // bring back the list
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment_container, listFragment).commit();
        } else {
            super.onBackPressed();
        }
    }

    /*
     * Open the appropriate page when a nav item is selected.
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the home action
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment_container, listFragment).commit();
        } else if (id == R.id.nav_weights) {
            if (weightFragment == null) {
                weightFragment = WeightFragment.newInstance();
            }
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment_container, weightFragment).commit();
            projectPage = false;

        } else if (id == R.id.nav_info) {
            if (infoFragment == null) {
                infoFragment = InfoFragment.newInstance();
            }
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment_container, infoFragment).commit();
            projectPage = false;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    /*
     * When a project is selected, open it's page
     */
    @Override
    public void onListFragmentInteraction(Project project) {
        try {
            projectFragment = project.getNewFragment().apply(project);
        }
        catch (Exception e) {
        }
        getSupportFragmentManager().beginTransaction().
                replace(R.id.fragment_container, projectFragment).commit();
        projectPage = true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
