package com.rninteractionwithnative;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static com.rninteractionwithnative.MapUtil.toMap;
import static com.rninteractionwithnative.MapUtil.toWritableMap;

public class ReactOpenActivityModule extends ReactContextBaseJavaModule {
    private Promise mPromise;
    private static final String TAG = "ReactOpenActivityModule";
    private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {
        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
            try{
                switch (requestCode) {
                    case 1:
                        if (mPromise != null) {
                            // 因为父类的onBackPressed导致resultCode没对上... 要把super.onBackPressed()注释掉
                            if (resultCode == RESULT_OK) {
                                Map<String, Object> map = (Map<String, Object>) data.getSerializableExtra("dataBackFromActivity");
                                WritableMap writableMap = toWritableMap(map);
                                // 回传给RN的Object类型还是得用WritableMap类型封装
                                mPromise.resolve(writableMap);
                            }
                        }
                        break;
                    default:
                        break;
                }

            } catch (Throwable e) {
                Log.d(TAG, "onActivityResult: " + e.getMessage());
            }

            mPromise = null;
        }
    };
    
    public ReactOpenActivityModule(@Nullable @org.jetbrains.annotations.Nullable ReactApplicationContext reactContext) {
        super(reactContext);
        reactContext.addActivityEventListener(mActivityEventListener);
    }

    @NonNull
    @NotNull
    @Override
    public String getName() {
        return "ReactOpenActivityModule";
    }

    @ReactMethod
    public void openActivityWithName(ReadableMap readableMap, Promise promise) {
        try {

            Map<String, Object> map = toMap(readableMap);

            if (!map.containsKey("activityName")) {
                promise.reject("-1", "not have activityName attribute");
                return;
            }

            if (map.get("activityName") == null) {
                promise.reject("-1", "post a null activityName");
                return;
            }
            String activityName = "com.rninteractionwithnative." + (String) map.get("activityName");

            Intent intent = new Intent(getCurrentActivity(), Class.forName(activityName));

            Iterator<String> iterator = map.keySet().iterator();

            // boolean b = map.keySet().keySet(key -> key == "activityName");
            // 为了防止不兼容，删除map某个元素的还是用下面的方法，而不是上面的高级用法
            // Java中如何遍历Map对象的4种方法: https://blog.csdn.net/tjcyjd/article/details/11111401
            while (iterator.hasNext()) {
                String key = iterator.next();

                // Java字符串比较（3种方法）：http://c.biancheng.net/view/5808.html
                if (key.equals("activityName")) {
                    iterator.remove();
                }
            }
            intent.putExtra("extraData", (Serializable) map);

            getCurrentActivity().startActivity(intent);
            // Activity openedActivity = (Activity) map.get("activityName");
            // 没有找到把Class转成 Activity对象的方法，不知道怎么调用TargetActivity.actionStart()

        } catch (Throwable e) {
            promise.reject(e);
        }
    }

    @ReactMethod
    public void openActivityWithNameAndResult(ReadableMap readableMap, Promise promise) {
        mPromise = promise;
        try {

            Map<String, Object> map = toMap(readableMap);

            if (!map.containsKey("activityName")) {
                promise.reject("-1", "not have activityName attribute");
                return;
            }

            if (map.get("activityName") == null) {
                promise.reject("-1", "post a null activityName");
                return;
            }
            String activityName = "com.rninteractionwithnative." + (String) map.get("activityName");

            Intent intent = new Intent(getCurrentActivity(), Class.forName(activityName));

            Iterator<String> iterator = map.keySet().iterator();

            // boolean b = map.keySet().keySet(key -> key == "activityName");
            // 为了防止不兼容，删除map某个元素的还是用下面的方法，而不是上面的高级用法
            // Java中如何遍历Map对象的4种方法: https://blog.csdn.net/tjcyjd/article/details/11111401
            while (iterator.hasNext()) {
                String key = iterator.next();

                // Java字符串比较（3种方法）：http://c.biancheng.net/view/5808.html
                if (key.equals("activityName")) {
                    iterator.remove();
                }
            }
            intent.putExtra("extraData", (Serializable) map);

            getCurrentActivity().startActivityForResult(intent, 1);
            // Activity openedActivity = (Activity) map.get("activityName");
            // 没有找到把Class转成 Activity对象的方法，不知道怎么调用TargetActivity.actionStart()

        } catch (Throwable e) {
            promise.reject(e);
        }
    }

}
