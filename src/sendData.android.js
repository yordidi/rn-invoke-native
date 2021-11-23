import {NativeModules} from 'react-native';

const rnModuleAdapter = (methodName, params) => {
  const reactMoudleName = 'ReactNativeModule';

  const api = NativeModules?.[reactMoudleName]?.[methodName];

  if (typeof api === 'function') {
    api(params);
    return Promise.resolve(
      `Android's ${reactMoudleName}.${methodName} Apis invoke success`,
    );
  } else {
    return Promise.reject(
      `Android's ${reactMoudleName}.${methodName} Apis is not a function`,
    );
  }
};

/**
 * js 8种数据类型
 * https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Data_structures
 * String、Number、BigInt、Boolean、Symbol、undefined、null
 * Object
 */

/**
 * Android-java 8种数据类型 以及 数据类型之间的转换：
 * 基本数据类型：char、int、float、double、long、short、boolean、byte
 * 引用数据类型：List、ArrayList、Map、Set、HashMap
 * 字符相关类：String、StringBuilder
 * https://blog.csdn.net/vaecer/article/details/21822613
 * https://www.jianshu.com/p/948f32b04fa5
 * https://blog.csdn.net/u011216417/article/details/45482523
 */

/**
 * @abstract String类型，可以传String、undefined、null
 */
export const sendString = params => {
  // rnModuleAdapter('sendStringData', '100')  // 100
  // rnModuleAdapter('sendStringData', 100)  // error
  // rnModuleAdapter('sendStringData', false)  // error
  // rnModuleAdapter('sendStringData', undefined) // null
  rnModuleAdapter('sendStringData', null) // null
    .then(res => console.log('res :>> ', res))
    .catch(e => console.log('e :>> ', e));
};
/**
 * @abstract Number类型，当Android端用 int类型接收时，只保留整数位。当Android端用 float、double类型接收时，可完整传过去。
 */
export const sendNumber = params => {
  // rnModuleAdapter('sendNumberData', 0.51) // int接收值0，不会四舍五入
  // rnModuleAdapter('sendNumberData', 0.5151515151515151515151515151515151) // float接收值0.5151515
  rnModuleAdapter('sendNumberData', 0.5151515151515151515) // doubule接收值0.5151515151515151515
    .then(res => console.log('res :>> ', res))
    .catch(e => console.log('e :>> ', e));
};
/**
 * @abstract 传递Boolean类型，没有问题。只是Java的Boolean与int类型没办法转换？？？
 */
export const sendBoolean = params => {
  rnModuleAdapter('sendBooleanData', false)
    .then(res => console.log('res :>> ', res))
    .catch(e => console.log('e :>> ', e));
};
/**
 * @abstract 传递数组
 * rn数组元素可以有有任何类型，android怎么接收呢？？？
 * 数组是有序的，因此取值时要根据类型
 */
export const sendArray = params => {
  // rnModuleAdapter('sendArrayData', ['100', 'dididi']) // 100 dididid
  rnModuleAdapter('sendArrayData', [100.55, 'string', false, undefined, null]) // 100.55 string false null null
    .then(res => console.log('res :>> ', res))
    .catch(e => console.log('e :>> ', e));
};
/**
 * @abstract 传递字典
 * rn的object可以有任何类型，android怎么接收？？？
 */
export const sendDict = params => {
  rnModuleAdapter('sendDictData', {name: 'diidi', age: 18}) // dididi 18.0
    .then(res => console.log('res :>> ', res))
    .catch(e => console.log('e :>> ', e));
};
