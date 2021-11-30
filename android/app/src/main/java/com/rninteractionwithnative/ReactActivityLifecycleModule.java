package com.rninteractionwithnative;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class ReactActivityLifecycleModule extends ReactContextBaseJavaModule implements LifecycleEventListener {
    private static final String TAG = "ReactActivityLifecycleM";
    private final ReactApplicationContext mContext;

    public ReactActivityLifecycleModule(@Nullable @org.jetbrains.annotations.Nullable ReactApplicationContext reactContext) {
        super(reactContext);
        mContext = reactContext;
        reactContext.addLifecycleEventListener(this);
    }

    @NonNull
    @NotNull
    @Override
    public String getName() {
        return "ReactActivityLifecycleModule";
    }


    @Override
    public void onHostResume() {
        Log.d(TAG, "onHostResume: ");
        sendEventToRN("push", "onHostResume");
    }

    @Override
    public void onHostPause() {
        Log.d(TAG, "onHostPause: ");
        // 采用Event Emit的方式，因为Promise只可以用一次。不太理解应用场景
        sendEventToRN("push", "onHostPause");
    }

    @Override
    public void onHostDestroy() {
        Log.d(TAG, "onHostDestroy: ");
        sendEventToRN("push", "onHostDestroy");

    }
    @ReactMethod
    public void openActivity() {
        Log.d(TAG, "foo: ");
        Activity currentActivity = getCurrentActivity();
        Intent intent = new Intent(currentActivity, CustomActivityForOpenedByReactNative.class);

        getCurrentActivity().startActivity(intent);
    }
    // 发送事件通知给RN
    public void sendEventToRN(String eventName, String msg) {
        mContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, msg);
    }
}
