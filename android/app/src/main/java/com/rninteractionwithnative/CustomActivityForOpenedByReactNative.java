package com.rninteractionwithnative;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CustomActivityForOpenedByReactNative extends AppCompatActivity {
    private static final String TAG = "ForOpenedByReactNative";
    private Map<String, Object> map = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_opened_by_react_nativie);
        try {
            Intent intent = getIntent();
//            Map<String, Object> map = new HashMap<>();
            map = (Map<String, Object>) intent.getSerializableExtra("extraData");
            Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = iterator.next();
                String key = (String) entry.getKey();
                Object value = entry.getValue();
                if (value == null) {
                    Log.d(TAG, key + "：null");
                } else if (value instanceof Map) {
                    Log.d(TAG, key + "：map");
                } else if (value.getClass() != null && value.getClass().isArray()) {
                    Log.d(TAG, key + "：array");
                } else {
                    Log.d(TAG, key + "：" + value);
                }
            }
        } catch (Exception e){
            Log.e(TAG, "getIntentData error: ", e);
        }


    }
    // 这个方法不知道该怎么被React Native Activity调用，因为ActivityName 是不确定的
    public static void actionStart(Context context, Map<String, Object> map) {
        Intent intent = new Intent(context, CustomActivityForOpenedByReactNative.class);
        intent.putExtra("extraData", (Serializable) map);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // 因为调用了setResult 和 finish，因此需要把父类的方法注释掉
        // super.onBackPressed();
        Intent intent = new Intent();
        for (String key :
                map.keySet()) {
            Log.d(TAG, "onBackPressed: key is " + key);
        }
        intent.putExtra("dataBackFromActivity", (Serializable) map);
//        intent.putExtra("dataBackFromActivity", "(Serializable) map");

        setResult(RESULT_OK, intent);
        finish();
    }
}