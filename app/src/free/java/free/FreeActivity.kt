package com.inqint.yarnrequirements.free

import android.os.Bundle
import android.util.Log
import com.amazon.device.ads.*
import com.inqint.yarnrequirements.BuildConfig
import com.inqint.yarnrequirements.MainActivity
import com.inqint.yarnrequirements.R
import free.LOG_TAG
import free.amazonAppKey



open class FreeActivity : MainActivity() {

    protected lateinit var adView:AdLayout
    protected var adOptions:AdTargetingOptions = AdTargetingOptions()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AdRegistration.setAppKey(amazonAppKey);
        if(BuildConfig.BUILD_TYPE.equals("debug")) {
            AdRegistration.enableTesting(true);
            AdRegistration.enableLogging(true);
        }

        this.adView = findViewById<AdLayout>(R.id.adview)
        this.adView.setListener(AdListener())

        adOptions.enableGeoLocation(true)

        loadAd()
        showAd()
    }

    /**
     * Load a new ad.
     */
    fun loadAd() {
        // Load an ad with default ad targeting.
        this.adView.loadAd(adOptions)

        // Note: You can choose to provide additional targeting information to modify how your ads
        // are targeted to your users. This is done via an AdTargetingOptions parameter that's passed
        // to the loadAd call. See an example below:
        //
        //    final AdTargetingOptions adOptions = new AdTargetingOptions();
        //    adOptions.enableGeoLocation(true);
        //    this.adView.loadAd(adOptions);
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
