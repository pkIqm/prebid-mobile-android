package org.prebid.mobile.demoapp;

import android.app.Application;
import android.os.Build;
import android.webkit.WebView;

import org.prebid.mobile.core.AdUnit;
import org.prebid.mobile.core.BannerAdUnit;
import org.prebid.mobile.core.InterstitialAdUnit;
import org.prebid.mobile.core.Prebid;
import org.prebid.mobile.core.PrebidException;
import org.prebid.mobile.core.TargetingParams;

import java.util.ArrayList;

import static org.prebid.mobile.demoapp.Constants.DFP_BANNER_ADUNIT_300x250;
import static org.prebid.mobile.demoapp.Constants.DFP_BANNER_ADUNIT_320x50;

public class PrebidApplication extends Application {
    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     * Implementations should be as quick as possible (for example using
     * lazy initialization of state) since the time spent in this function
     * directly impacts the performance of starting the first activity,
     * service, or receiver in a process.
     * If you override this method, be sure to call super.onCreate().
     */
    @Override
    public void onCreate() {
        super.onCreate();


        // Clear WebView Cache when needed
        WebView obj = new WebView(this);
        obj.clearCache(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && BuildConfig.DEBUG) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        /**
         * Initialise prebid for DFP ad unit
         */
        initialisePrebidForDFP();
    }

    private void initialisePrebidForDFP() {

        ArrayList<AdUnit> adUnits = new ArrayList<AdUnit>();

        //Configure Ad-Slot1
        BannerAdUnit adUnit1 = new BannerAdUnit(DFP_BANNER_ADUNIT_320x50, "138c4d03-0efb-4498-9dc6-cb5a9acb2ea4");
        adUnit1.addSize(320, 50);

        //Configure Ad-Slot2 with the same demand source
        BannerAdUnit adUnit2 = new BannerAdUnit(DFP_BANNER_ADUNIT_300x250, "0c286d00-b3ee-4550-b15d-f71f8e746865");
        adUnit2.addSize(300, 250);

        //Configure Interstitial Ad Unit
        InterstitialAdUnit adUnit3 = new InterstitialAdUnit("Interstitial", "35f1d17d-c99a-4d55-800e-062b80750d65");

        //Configure Native Ad Unit
//        BannerAdUnit adUnit4 = new BannerAdUnit("Native");
//        adUnit4.addSize(300, 250);

        // Add Configuration
//        adUnits.add(adUnit1);
        adUnits.add(adUnit2); // FIXME add the ad units back when done testing
        adUnits.add(adUnit3);
//        adUnits.add(adUnit4);

        // Set targeting
        TargetingParams.setGender(TargetingParams.GENDER.FEMALE);
        TargetingParams.setAge(25);
        TargetingParams.setLocationDecimalDigits(2);
        TargetingParams.setLocationEnabled(true);
        TargetingParams.setCustomTargeting("Test", "Prebid-Custom-1");
        TargetingParams.setCustomTargeting("Test", "Prebid-Custom-2");
        ArrayList<String> values = new ArrayList<String>();
        values.add("Prebid-Custom-2");
        TargetingParams.setCustomTargeting("Test2", values);

        // Register  adslots for prebid.
        try {
            Prebid.init(getApplicationContext(), adUnits, "bfa84af2-bd16-4d35-96ad-31c6bb888df0");
        } catch (PrebidException e) {
            e.printStackTrace();
        }

    }
}
