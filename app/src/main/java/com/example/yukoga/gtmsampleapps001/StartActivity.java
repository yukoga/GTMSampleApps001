package com.example.yukoga.gtmsampleapps001;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tagmanager.Container;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.TagManager;

import java.util.Map;

public class StartActivity extends AppCompatActivity {
    private static long TIMEOUT_FOR_CONTAINER_OPEN_MILLISECONDS = 2000;
    private String SCREEN_NAME = "Start Activity";
    private String CONTAINER_ID = "GTM-TJ4654";
    private DataLayer mDataLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        TagManager tagManager = TagManager.getInstance(this);
        tagManager.setVerboseLoggingEnabled(true);

        PendingResult<ContainerHolder> pending = tagManager.
                loadContainerPreferNonDefault(CONTAINER_ID, R.raw.default_container_binary);
        pending.setResultCallback(new ResultCallback<ContainerHolder>() {
            @Override
            public void onResult(@NonNull ContainerHolder containerHolder) {
                ContainerHolderSingleton.setContainerHolder(containerHolder);
                Container container = containerHolder.getContainer();
                if (!containerHolder.getStatus().isSuccess()) {
                    Log.e("GTMSampleApps001", "Failed to load container.");
                    return;
                }
                ContainerLoadedCallback.registerCallbacksForContainer(container);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mDataLayer = TagManager.getInstance(this).getDataLayer();
        mDataLayer.push(DataLayer.mapOf("event", "openScreen", "screenName", SCREEN_NAME));
    }

    private static class ContainerLoadedCallback implements ContainerHolder.ContainerAvailableListener {
        @Override
        public void onContainerAvailable(ContainerHolder containerHolder, String containerVersion) {
            Container container = containerHolder.getContainer();
            registerCallbacksForContainer(container);
        }

        public static void registerCallbacksForContainer(Container container) {
            container.registerFunctionCallTagCallback("myCustomTag", new CustomTagCallBack());
        }

        private static class CustomTagCallBack implements Container.FunctionCallTagCallback {
            @Override
            public void execute(String tagName, Map<String, Object> parameters) {
                Log.i("GTMSampleApps001", "Custom Function Call tag : " + tagName + "is fired.");
            }
        }
    }
}
