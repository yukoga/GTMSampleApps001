package com.example.yukoga.gtmsampleapps001;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tagmanager.InstallReferrerReceiver;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.TagManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class ReferrerReceiver extends BroadcastReceiver {
    private DataLayer mDataLayer;
    private Map<String, String> myReferrer;


    public ReferrerReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String referrer = intent.getStringExtra("referrer");

        try {
            myReferrer = getHashMapFromReferrer(referrer);

            for (String key : myReferrer.keySet()) {
                Log.i("REFERRER", "key is : " + key + " and value is : " + myReferrer.get(key));
            }
        } catch (Exception e) {
            Log.e("Error on referrer:", e.getMessage());
        } finally {
            Log.i("REFERRER", "Finish capture install referrer.");
//            if (myReferrer.containsKey("gclid")) {
//                InstallReferrerReceiver receiver = new InstallReferrerReceiver();
//                receiver.onReceive(context, intent);
//                Log.i("REFERRER", "Found gclid in the referrer. gclid : " + myReferrer.get("gclid"));
//            } else {
//                Log.i("REFERRER", "Found utm_source in the referrer. utm_source : " + myReferrer.get("utm_source"));
            mDataLayer = TagManager.getInstance(context).getDataLayer();
            mDataLayer.push(DataLayer.mapOf("event", "captureReferrer",
                    "screenName", "On Receive Referrer",
                    "screenName", referrer,
                    "gclid", myReferrer.get("gclid"),
                    "mySource", myReferrer.get("utm_source"),
                    "myMedium", myReferrer.get("utm_medium"),
                    "myCampaign", myReferrer.get("utm_campaign")));
//            InstallReferrerReceiver receiver = new InstallReferrerReceiver();
//            receiver.onReceive(context, intent);
//            }
        }
    }

    public Map<String, String> getHashMapFromReferrer(String referrer) {
        Map<String, String> params = new HashMap<String, String>();
        Uri uri = Uri.parse("http://hostname/?" + referrer);
        for (Iterator keys = uri.getQueryParameterNames().iterator(); keys.hasNext();) {
            String key = (String) keys.next();
            params.put(key, uri.getQueryParameter(key));
        }
        return params;
    }
}
