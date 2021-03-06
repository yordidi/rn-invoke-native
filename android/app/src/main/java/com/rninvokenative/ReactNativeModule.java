package com.rninvokenative;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.IllegalViewOperationException;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.rninvokenative.ArrayUtil.toArray;
import static com.rninvokenative.ArrayUtil.toJSONArray;
import static com.rninvokenative.ArrayUtil.toWritableArray;
import static com.rninvokenative.MapUtil.toJSONObject;
import static com.rninvokenative.MapUtil.toMap;
import static com.rninvokenative.MapUtil.toWritableMap;

public class ReactNativeModule extends ReactContextBaseJavaModule {
    private static final String TAG = "ReactNativeModule";

    private final ReactApplicationContext mContext;

    public ReactNativeModule(ReactApplicationContext context) {
        mContext = context;
    }

    @NonNull
    @NotNull
    @Override
    public String getName() {
        return "ReactNativeModule";
    }

    @ReactMethod
    public void sendStringData(String str) {
        Toast.makeText(mContext, str, Toast.LENGTH_LONG).show();
    }

    @ReactMethod
    public void sendArrayData(ReadableArray readableArray) {
        ArrayList<Object> arrayList = readableArray.toArrayList();
        for (Object obj: arrayList) {
            Log.d(TAG, "sendArrayData: " + obj);
        }
        Toast.makeText(mContext, "success", Toast.LENGTH_LONG).show();
    }

    @ReactMethod
    public void sendDictData(ReadableMap readableMap) {
        HashMap<String, Object> hashMap = readableMap.toHashMap();
        for (Object key: hashMap.keySet()) {
            Log.d(TAG, "sendDictData: " + hashMap.get(key));
        }
        Toast.makeText(mContext, "success", Toast.LENGTH_LONG).show();
    }

    @ReactMethod
    public void sendNumberData(double num) {

        Toast.makeText(mContext, String.valueOf(num), Toast.LENGTH_LONG).show();
    }

    @ReactMethod
    public void sendBooleanData(boolean num) {

        Toast.makeText(mContext, String.valueOf(num), Toast.LENGTH_LONG).show();
    }
    // Readable Writeable????????????
//    https://gist.github.com/mfmendiola/bb8397162df9f76681325ab9f705748b
    @ReactMethod
    public void sendDataWithCallback(ReadableArray readableArray, Callback callback) {
        try {
            Log.d(TAG, "sendDataWithCallback: ");
            // toArray??????????????????????????????????????????????????????JSONArray,??????????????????????????????????????????JSONArray??????
            // ??????1?????????1.0??????????????????????????????????????????ReadableArray???????????????????????????????????????
            // JSONArray jsonArray = toJSONArray(readableArray);
            // Object[] objects = toArray(jsonArray);
            Object[] objects = toArray(readableArray);
            for (Object obj: objects) {
                Log.d(TAG, "objects: " + obj);
            }
            WritableArray writableArray = toWritableArray(objects);
            Toast.makeText(mContext, "success", Toast.LENGTH_LONG).show();
            callback.invoke(null, writableArray, "extra data");
        } catch (IllegalViewOperationException e) {
            callback.invoke(e.getMessage(), "error data from android");
        }

    }

    @ReactMethod
    public void sendDataWithPromise(ReadableMap readableMap, Promise promise) throws Exception {
        try {
            // toMap?????????????????????????????????????????????null?????????????????????????????????????????????Array????????????????????????Object???
            // js???????????????????????????????????????
            // JSONObject jsonObject = toJSONObject(readableMap);
            // Map<String, Object> map = toMap(jsonObject);
            Map<String, Object> map = toMap(readableMap);
            WritableMap writableMap = toWritableMap(map);

            Toast.makeText(mContext, "success", Toast.LENGTH_LONG).show();
            promise.resolve(writableMap);
        } catch (IllegalViewOperationException e) {
            promise.reject("-1", e);
        }

    }
}
