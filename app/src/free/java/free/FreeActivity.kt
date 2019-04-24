package com.inqint.yarnrequirements.free

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.navigation.findNavController
import com.google.ads.consent.*
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.inqint.yarnrequirements.MainActivity
import com.inqint.yarnrequirements.R
import java.net.MalformedURLException
import java.net.URL
import java.util.*



open class FreeActivity : MainActivity() {

    private var showPersonlAds: Boolean = false
    lateinit var form: ConsentForm
    lateinit var adView : AdView
    protected lateinit var adTimer: Timer
    protected lateinit var reqTimer: Timer
    protected lateinit var adProviders: List<AdProvider>
    protected val context = this
    private val TAG = "MainActivity"
    private val SHOW_PERSONAL_ADS_KEY = "show.personal.ads.key"

    protected override val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
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
            R.id.navigation_privacy -> {
                navController.navigate(R.id.action_global_privacy)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null)
            showPersonlAds = savedInstanceState.getBoolean(SHOW_PERSONAL_ADS_KEY)

        // Admob
        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, getString(R.string.admob_id))

        adView = findViewById(R.id.adView)
        checkAdConsent()
    }

    private fun checkAdConsent() {
        val consentInformation = ConsentInformation.getInstance(this)
        consentInformation.debugGeography = DebugGeography.DEBUG_GEOGRAPHY_EEA;
        val publisherIds = arrayOf("pub-3658144280100378")
        consentInformation.requestConsentInfoUpdate(publisherIds, object : ConsentInfoUpdateListener {
            override fun onConsentInfoUpdated(consentStatus: ConsentStatus) {
                // User's consent status successfully updated.
                adProviders = ConsentInformation.getInstance(context).adProviders
                when (consentStatus) {
                    ConsentStatus.PERSONALIZED -> loadAds(true)
                    ConsentStatus.NON_PERSONALIZED -> loadAds(false)
                    ConsentStatus.UNKNOWN ->
                    {
                        if (consentInformation.isRequestLocationInEeaOrUnknown) {
                            requestConsent()
                        }
                    }
                }
                Log.d(TAG, "onConsentInfoUpdated, Consent Status = ${consentStatus.name}")
            }

            override fun onFailedToUpdateConsentInfo(errorDescription: String) {
                // User's consent status failed to update.
            }
        })
    }

    private fun requestConsent() {
        var privacyUrl: URL? = null
        try {
            privacyUrl = URL("https://deb761.github.io")
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            // Handle error.
        }

        form = ConsentForm.Builder(this, privacyUrl)
            .withListener(object : ConsentFormListener() {
                override fun onConsentFormLoaded() {
                    // Consent form loaded successfully.
                    super.onConsentFormLoaded()
                    form.show()
                }

                override fun onConsentFormOpened() {
                    // Consent form was displayed.
                }

                override fun onConsentFormClosed(
                    consentStatus: ConsentStatus?, userPrefersAdFree: Boolean?
                ) {
                    super.onConsentFormClosed(consentStatus, userPrefersAdFree)
                    // Consent form was closed.
                    ConsentInformation.getInstance(context).consentStatus = consentStatus

                    if (userPrefersAdFree != null && userPrefersAdFree) {
                        // take user to play store to purchase paid version
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse(
                                "https://play.google.com/store/apps/details?id=com.inqint.yarnrequirements.full")
                            setPackage("com.android.vending")
                        }
                        startActivity(intent)

                    }

                    when (consentStatus) {
                        ConsentStatus.PERSONALIZED -> loadAds(true)
                        ConsentStatus.NON_PERSONALIZED -> loadAds(false)
                        ConsentStatus.UNKNOWN -> loadAds(true)
                    }

                }

                override fun onConsentFormError(errorDescription: String?) {
                    // Consent form error.
                    println(errorDescription)
                }
            })
            .withPersonalizedAdsOption()
            .withNonPersonalizedAdsOption()
            .withAdFreeOption()
            .build()

        form.load()
    }

    private fun loadAds(showPersonlAds: Boolean) {
        this.showPersonlAds = showPersonlAds
        val build: AdRequest
        if (!this.showPersonlAds)
            build = AdRequest.Builder()
                .addNetworkExtrasBundle(AdMobAdapter::class.java, getNonPersonalizedAdsBundle())
                .build()
        else
            build = AdRequest.Builder().build()

        adView.adListener = object : AdListener() {
            override fun onAdClosed() {
                adView.loadAd(build)
                Log.d(TAG, "onAdClosed Banner Ad")
            }
            override fun onAdFailedToLoad(p0: Int) {
                super.onAdFailedToLoad(p0)
                adView.loadAd(build)
                Log.d(TAG, "onAdFailedToLoad Banner Ad")
            }
        }
        adView.loadAd(build)
    }

    private fun getNonPersonalizedAdsBundle(): Bundle {
        val extra = Bundle()
        extra.putString("npa", "1")
        return extra
    }

    // Called when leaving the activity
    public override fun onPause() {
        adView.pause()
        super.onPause()
    }

    // Called when returning to the activity
    public override fun onResume() {
        super.onResume()
        adView.resume()
    }

    // Called before the activity is destroyed
    public override fun onDestroy() {
        adView.destroy()
        super.onDestroy()
        reqTimer.cancel()
        adTimer.cancel()
    }
    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.putBoolean(SHOW_PERSONAL_ADS_KEY, showPersonlAds)
    }
}
