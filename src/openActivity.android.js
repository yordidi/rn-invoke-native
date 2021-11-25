import {NativeModules} from 'react-native';

export const openActivityWithName = params => {
  NativeModules.ReactOpenActivityModule.openActivityWithName({
    activityName: 'CustomActivityForOpenedByReactNative',
    str: 'str',
    undef: 'undefined',
    nil: null,
    num: 100,
    float: 1.05,
    mapData: {
      a: 1,
      b: 'b',
      c: false,
    },
    list: Array(3).fill(1),
  })
    .then(res => console.log('res', res))
    .catch(e => console.log('e', e));
};

export const openActivityWithNameAndResult = params => {
  NativeModules.ReactOpenActivityModule.openActivityWithNameAndResult({
    activityName: 'CustomActivityForOpenedByReactNative',
    str: 'str',
    undef: 'undefined',
    nil: null,
    num: 100,
    float: 1.05,
    mapData: {
      a: 1,
      b: 'b',
      c: false,
    },
    list: Array(3).fill(1),
  })
    .then(res => console.log('res', res))
    .catch(e => console.log('e', e));
};
