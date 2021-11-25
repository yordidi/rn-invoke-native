package com.rninteractionwithnative;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MyReactNativePackage implements com.facebook.react.ReactPackage {
    @NonNull
    @NotNull
    @Override
    public List<NativeModule> createNativeModules(@NonNull @NotNull ReactApplicationContext reactContext) {
        List<NativeModule> list = new ArrayList<>();
        list.add(new ReactNativeModule(reactContext));
        list.add(new ReactEventEmitterModule(reactContext));
        return list;
    }

    @NonNull
    @NotNull
    @Override
    public List<ViewManager> createViewManagers(@NonNull @NotNull ReactApplicationContext reactContext) {
        List<ViewManager> list = new ArrayList<>();

        return list;
    }
}
