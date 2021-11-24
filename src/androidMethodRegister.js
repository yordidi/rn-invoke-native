/**
 * @abstract Android端方法注册
 * 功能1 暴露Native方法
 * 功能2 检测Native方法是否存在
 * 功能3 检测Natvie方法调用入参类型是否合理
 * 需要统一入口进行注册吗？按照es6模块化思想，避免全局对象，在调用的地方注册，随即调用。其实也可以在一个文件里统一注册，暴露
 * 内部的函数给业务模块调用，这样的好处是便于模块化，便于排查问题。
 */

import {NativeModules} from 'react-native';

export const registerNativeMethod = function registerNativeMethod(
  moduleName,
  methodName,
  ...paramsTypes
) {
  return function (...params) {
    const nativeMethod = NativeModules?.[moduleName]?.[methodName];

    if (typeof nativeMethod !== 'function') {
      const errorMessage = `not have a method named ${methodName} on ${moduleName}`;
      if ('开发模式') {
        console.warn(errorMessage);
      }
      return Promise.reject(errorMessage);
    }

    if (params.length !== paramsTypes.length) {
      const errorMessage = `post to ${methodName} on ${moduleName} with error data length`;
      if ('开发模式') {
        console.warn(errorMessage);
      }
      return Promise.reject(errorMessage);
    }
    if (params.length > 0) {
      const isUnCompatible = paramsTypes.some((t, i) => typeof params[i] !== t);
      if (isUnCompatible) {
        const errorMessage = `call ${methodName} on ${moduleName} with error data type`;
        if ('开发模式') {
          console.warn(errorMessage);
        }
        return Promise.reject(errorMessage);
      }
    }
    return nativeMethod.apply(null, params);
  };
};

/**
 * example
 */

export const showToast = params => {
  return registerNativeMethod('NativeModule', 'showToast', 'string')(params);
};
