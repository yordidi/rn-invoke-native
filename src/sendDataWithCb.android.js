import {NativeModules} from 'react-native';

/**
 * @abstract 传递function
 */
export const sendDataWithCallback = params => {
  const moduleName = 'ReactNativeModule';
  const methodName = 'sendDataWithCallback';
  const api = NativeModules?.[moduleName]?.[methodName];
  //   const invokeParams = [1, 2, 3, 4]  // ["1.0", "2.0", "3.0", "4.0"]
  //   const invokeParams = [1, '2', 'abc', false, null, undefined]; // ["1.0", "2", "abc", "false", "null", "null"]
  //   const invokeParams = [null, undefined, [1]]; // ["null", "null", "[1.0]"]  此时Android端用的是WriteableArray.putString方法
  //   const invokeParams = [false, 1, '3', [1]]; //用ArrayUtil之后，[false, 1, "3", [1]]
  //   const invokeParams = [1, '2', {name: 'welcom'}]; // [1, "2", {"name": "welcom"}]
  //   const invokeParams = ['2', {name: 'welcom'}, undefined]; // ["2", {"name": "welcom"}, null]
  //   const invokeParams = [null, {name: 'welcom'}, undefined, {key: {an: 'yes'}}]; // [null, {"name": "welcom"}, null, {"key": {"an": "yes"}}]
  const invokeParams = 'error data type'; // 红屏

  if (typeof api === 'function') {
    api(invokeParams, (err, data) => {
      if (!err) {
        console.log('data :>> ', data);
      } else {
        console.log('err :>> ', err);
      }
    });
  } else {
    console.log(`NativeModules.${moduleName}.${methodName} is not function`);
  }
};

/**
 * @abstract 传递Promise。数据类型错误，并不会被rn、android端的try catche捕获，因此调用之前需要校验数据类型是否合法
 */
export const sendDataWithPromise = params => {
  const moduleName = 'ReactNativeModule';
  const methodName = 'sendDataWithPromise';
  const api = NativeModules?.[moduleName]?.[methodName];
  //   const invokeParams = {
  //     book: {bookName: 'humem'},
  //     author: {name: 'jim', desc: 'from Star'},
  //   }; // {"author": {"desc": "from Star", "name": "jim"}, "book": {"bookName": "humem"}}
  const invokeParams = {
    book: {bookName: undefined, age: 18, has: false, follows: [1, '2', true]},
    author: {name: null, desc: 'from Star'},
  }; // {"author": {"desc": "from Star"}, "book": {"age": 18, "follows": [1, "2", true], "has": false}} 。。。 undefined和null给抹掉了
  if (typeof api === 'function') {
    // try {
    //   api(100) // 会红屏，不会被try catch捕获
    //     .then(res => console.log('res :>> ', res))
    //     .catch(e => console.log('e :>> ', e));
    // } catch (error) {
    //   console.log('error :>> ', error);
    // }
    api(invokeParams)
      .then(res => console.log('res :>> ', res))
      .catch(e => console.log('e :>> ', e));
  } else {
    console.log(`NativeModules.${moduleName}.${methodName} is not function`);
  }
};
