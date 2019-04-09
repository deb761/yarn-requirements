package com.inqint.yarnrequirements.free

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.amazon.device.ads.*
import com.inqint.yarnrequirements.BuildConfig
import com.inqint.yarnrequirements.MainActivity
import com.inqint.yarnrequirements.R
import free.LOG_TAG
import free.amazonAppKey
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.fixedRateTimer


open class FreeActivity : MainActivity() {

    protected lateinit var adView:AdLayout
    protected var adOptions:AdTargetingOptions = AdTargetingOptions()
    protected lateinit var adTimer: Timer
    protected lateinit var reqTimer: Timer

    private val adHandler: Handler = object : Handler(Looper.getMainLooper()) {
        /*
         * handleMessage() defines the operations to perform when
         * the Handler receives a new Message to process.
         */
        override fun handleMessage(inputMessage: Message) {
            loadAd()
            showAd()
        }
    }

    private val reqHandler: Handler = object : Handler(Looper.getMainLooper()) {
        /*
         * handleMessage() defines the operations to perform when
         * the Handler receives a new Message to process.
         */
        override fun handleMessage(inputMessage: Message) {
            checkLocationPermission()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AdRegistration.setAppKey(amazonAppKey);
        if(BuildConfig.BUILD_TYPE.equals("debug")) {
            AdRegistration.enableTesting(true);
            AdRegistration.enableLogging(true);
        }

        this.adView = findViewById<AdLayout>(R.id.adview)
        this.adView.setListener(AdListener())


        // Create a timer that will load a new add every minute after checking permissions
        adTimer = fixedRateTimer(name = "ad-timer",
            initialDelay = 0, period = TimeUnit.MINUTES.toMillis(1)) {
            val message = adHandler.obtainMessage()
            message.sendToTarget()
        }

        // Create a timer that will check permissions
        reqTimer = fixedRateTimer(name = "permission-timer",
            initialDelay = 0, period = TimeUnit.DAYS.toMillis(1)) {
            val message = reqHandler.obtainMessage()
            message.sendToTarget()
        }
    }

    // Cancel the timer when the activity exits
    override fun onDestroy() {
        super.onDestroy()
        adTimer.cancel()
        reqTimer.cancel()
    }

    private val MY_PERMISSIONS_REQUEST_ACCESS_LOCATION: Int = 7335
    private val LOCATION_PERMISSIONS = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)

    fun checkLocationPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) ||
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                val builder = AlertDialog.Builder(this@FreeActivity)

                builder.setMessage(getString(R.string.location_reason))
                    .setTitle(getString(R.string.permission_request_title))

                builder.setPositiveButton("OK") { _, which ->
                    // request permissions
                    ActivityCompat.requestPermissions(
                        this,
                        LOCATION_PERMISSIONS,
                        MY_PERMISSIONS_REQUEST_ACCESS_LOCATION
                    )
                }

                // Finally, make the alert dialog using builder
                val dialog: AlertDialog = builder.create()

                // Display the alert dialog on app interface
                dialog.show()
            }
            else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                    LOCATION_PERMISSIONS,
                    MY_PERMISSIONS_REQUEST_ACCESS_LOCATION)

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            adOptions.enableGeoLocation(true)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_ACCESS_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    adOptions.enableGeoLocation(true)
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    adOptions.enableGeoLocation(false)
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }
    /**
     * Load a new ad.
     */
    fun loadAd() {
        // Load an ad
        this.adView.loadAd(adOptions)
    }

    fun showAd() {
        if (!this.adView.showAd()) {
            Log.w(LOG_TAG, "The ad was not shown. Check the logcat for more information.")
        }
    }

    /**
     * This class is for an event listener that tracks ad lifecycle events.
     * It extends DefaultAdListener, so you can override only the methods that you need.
     */
    class AdListener : DefaultAdListener() {
        /**
         * This event is called once an ad loads successfully.
         */
        override fun onAdLoaded(ad: Ad?, adProperties: AdProperties?) {
            Log.i(LOG_TAG, adProperties!!.adType.toString() + " ad loaded successfully.")

        }

        /**
         * This event is called if an ad fails to load.
         */
        override fun onAdFailedToLoad(ad: Ad?, error: AdError) {
            Log.w(LOG_TAG, "Ad failed to load. Code: " + error.code + ", Message: " + error.message)
        }

        /**
         * This event is called after a rich media ad expands.
         */
        override fun onAdExpanded(ad: Ad?) {
            Log.i(LOG_TAG, "Ad expanded.")
            // You may want to pause your activity here.
        }

        /**
         * This event is called after a rich media ad has collapsed from an expanded state.
         */
        override fun onAdCollapsed(ad: Ad?) {
            Log.i(LOG_TAG, "Ad collapsed.")
            // Resume your activity here, if it was paused in onAdExpanded.
        }
    }
}
