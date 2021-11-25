package com.rninteractionwithnative;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ReactEventEmitterModule extends ReactContextBaseJavaModule {
    private final ReactApplicationContext mReactContext;
    private final String SUCCESSEVENT = "SUCCESSEVENT";

    public ReactEventEmitterModule(@Nullable @org.jetbrains.annotations.Nullable ReactApplicationContext reactContext) {
        super(reactContext);
        mReactContext = reactContext;
    }

    @NonNull
    @NotNull
    @Override
    public String getName() {
        return "ReactEventEmitterModule";
    }
    // 暴露给JS的常量
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public Map<String, Object> getConstants() {
        Map<String, Object> map = new HashMap<>();
        map.put(SUCCESSEVENT, SUCCESSEVENT);
        return map;
    }

    public void sendMsgToRN(String eventName, WritableArray writableArray) {
        mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, writableArray);
    }
}
