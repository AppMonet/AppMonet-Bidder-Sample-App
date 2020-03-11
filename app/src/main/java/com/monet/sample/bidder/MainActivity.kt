package com.monet.sample.bidder

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.monet.bidder.AppMonetAdSize
import com.monet.bidder.AppMonetErrorCode
import com.monet.bidder.AppMonetInterstitial
import com.monet.bidder.AppMonetView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var interstitial: AppMonetInterstitial

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupAppMonetView()
        setupAppMonetInterstitial()
        loadMrectAd()
        loadInterstitial()
        showInterstitial()
    }

    /**
     * Clean up
     */
    override fun onDestroy() {
        appMonetView.destroy()
        interstitial.destroy()
        super.onDestroy()
    }

    /**
     * Sets up AppMonet Interstitial.
     */
    private fun setupAppMonetInterstitial() {
        interstitial = AppMonetInterstitial(this, "a49430ee57ee4401a9eda6098726ce54")
        interstitial.interstitialAdListener = object : AppMonetInterstitial.InterstitialAdListener {
            override fun onInterstitialLoaded(interstitial: AppMonetInterstitial?) {
                showToast("Interstitial Loaded")
            }

            override fun onInterstitialShown(interstitial: AppMonetInterstitial?) {
                showToast("Interstitial Shown")
            }

            override fun onInterstitialFailed(
                interstitial: AppMonetInterstitial?,
                errorCode: AppMonetErrorCode?
            ) {
                showToast("Interstitial Failed")
                interstitial?.destroy()
            }

            override fun onInterstitialDismissed(interstitial: AppMonetInterstitial?) {
                showToast("Interstitial Dismissed")
                interstitial?.destroy()
            }

            override fun onInterstitialClicked(interstitial: AppMonetInterstitial?) {
                showToast("Interstitial Clicked")
            }
        }
    }

    /**
     * Sets up AppMonetView.
     */
    private fun setupAppMonetView() {
        appMonetView.adUnitId = "b03e6dccfe9e4abab02470a39c88d5dc"
        appMonetView.setAdSize(AppMonetAdSize(300, 250))
        appMonetView.bannerAdListener = object : AppMonetView.BannerAdListener {
            override fun onBannerLoaded(banner: AppMonetView?) {
                showToast("Banner Ad Loaded")
            }

            override fun onBannerFailed(banner: AppMonetView?, error: AppMonetErrorCode?) {
                showToast("Banner Failed to Load")
            }

            override fun onBannerClicked(banner: AppMonetView?) {
                showToast("Banner Clicked")
            }

        }
    }

    /**
     * Listener on mrect button that will trigger AppMonet's addBids method.
     */
    private fun loadMrectAd() {
        loadMrect.setOnClickListener {
            appMonetView.loadAd()
        }
    }

    /**
     * Listener on load interstitial button that will trigger load on AppMonetInterstitial.
     */
    private fun loadInterstitial() {
        loadInterstitial.setOnClickListener {
            setupAppMonetView()
            interstitial.load()
        }
    }

    /**
     * Listener on show interstitial button that will trigger show on AppMonetInterstitial.
     */
    private fun showInterstitial() {
        showInterstitial.setOnClickListener {
            if (interstitial.isReady) {
                interstitial.show()
            } else {
                showToast("Interstitial Is Not Ready")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}
