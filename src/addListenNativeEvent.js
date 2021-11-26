import {NativeModules, NativeEventEmitter} from 'react-native';

/**
 * 监听Activity的生命周期用来做什么呢？
 * 当打开一个新的Activity，或者当前Activity被遮挡时，会触发相应的生命周期函数
 * 注意：当返回RN Activity时，JS的异步任务会继续执行。所以当一个Activity返回到RN Activity时，可以通过生命周期函数通知，或者
 * 通过onActivityResult通知。
 */
export const invokeNative = params => {
  NativeModules.ReactActivityLifecycleModule.openActivity();
  let i = 0;

  setInterval(() => {
    console.log('setInterval :>> ', ++i);
  }, 1000);
};
// 监听Android的事件通知
export const eventEmitter = new NativeEventEmitter(
  NativeModules.ReactActivityLifecycleModule,
);
