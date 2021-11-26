/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React, {useEffect} from 'react';
import type {Node} from 'react';
import {
  SafeAreaView,
  ScrollView,
  StatusBar,
  StyleSheet,
  Text,
  TouchableHighlight,
  useColorScheme,
  View,
  NativeModules,
  NativeEventEmitter,
} from 'react-native';

import {
  Colors,
  DebugInstructions,
  Header,
  LearnMoreLinks,
  ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';

import {
  sendString,
  sendArray,
  sendBoolean,
  sendDict,
  sendNumber,
} from './src/sendData.android';

import {
  sendDataWithCallback,
  sendDataWithPromise,
} from './src/sendDataWithCb.android';

import {
  openActivityWithName,
  openActivityWithNameAndResult,
} from './src/openActivity.android';

import {registerNativeMethod} from './src/androidMethodRegister';
import {invokeNative, eventEmitter} from './src/addListenNativeEvent';

const Section = ({children, title}): Node => {
  const isDarkMode = useColorScheme() === 'dark';
  return (
    <View style={styles.sectionContainer}>
      <Text
        style={[
          styles.sectionTitle,
          {
            color: isDarkMode ? Colors.white : Colors.black,
          },
        ]}>
        {title}
      </Text>
      <Text
        style={[
          styles.sectionDescription,
          {
            color: isDarkMode ? Colors.light : Colors.dark,
          },
        ]}>
        {children}
      </Text>
    </View>
  );
};

const App: () => Node = () => {
  const isDarkMode = useColorScheme() === 'dark';

  const backgroundStyle = {
    backgroundColor: isDarkMode ? Colors.darker : Colors.lighter,
  };

  useEffect(() => {
    // eventEmitter的warn，是新版RN引起的，可以选择关闭：https://stackoverflow.com/questions/69538962/new-nativeeventemitter-was-called-with-a-non-null-argument-without-the-requir
    const listener = eventEmitter.addListener('push', msg =>
      console.log('msg>>>>', msg),
    );

    return () => {
      listener.remove();
    };
  }, []);

  function handlePress() {
    registerNativeMethod(
      'ReactNativeModule',
      'sendDataWithPromise',
      'object',
      'string',
    )('hello')
      .then(res => console.log('res', res))
      .catch(e => {
        console.log('e :>> ', e);
      });
  }

  return (
    <SafeAreaView style={backgroundStyle}>
      <StatusBar barStyle={isDarkMode ? 'light-content' : 'dark-content'} />
      <ScrollView
        contentInsetAdjustmentBehavior="automatic"
        style={backgroundStyle}>
        <Header />
        <View
          style={{
            backgroundColor: isDarkMode ? Colors.black : Colors.white,
          }}>
          <Section title="Native Modules">
            <View style={styles.container}>
              <TouchableHighlight
                style={styles.buttonContainer}
                onPress={() => sendString()}>
                <View style={styles.button}>
                  <Text>sendString</Text>
                </View>
              </TouchableHighlight>
              <TouchableHighlight
                style={styles.buttonContainer}
                onPress={() => sendNumber()}>
                <View style={styles.button}>
                  <Text>sendNumber</Text>
                </View>
              </TouchableHighlight>
              <TouchableHighlight
                style={styles.buttonContainer}
                onPress={() => sendBoolean()}>
                <View style={styles.button}>
                  <Text>sendBoolean</Text>
                </View>
              </TouchableHighlight>
              <TouchableHighlight
                style={styles.buttonContainer}
                onPress={() => sendArray()}>
                <View style={styles.button}>
                  <Text>sendArray</Text>
                </View>
              </TouchableHighlight>

              <TouchableHighlight
                style={styles.buttonContainer}
                onPress={() => sendDict()}>
                <View style={styles.button}>
                  <Text>sendDict</Text>
                </View>
              </TouchableHighlight>
              <TouchableHighlight
                style={styles.buttonContainer}
                onPress={() => sendDataWithCallback()}>
                <View style={styles.button}>
                  <Text>sendDataWithCallback</Text>
                </View>
              </TouchableHighlight>
              <TouchableHighlight
                style={styles.buttonContainer}
                onPress={() => sendDataWithPromise()}>
                <View style={styles.button}>
                  <Text>sendDataWithPromise</Text>
                </View>
              </TouchableHighlight>
              <TouchableHighlight
                style={styles.buttonContainer}
                onPress={handlePress}>
                <View style={styles.button}>
                  <Text>sendData with a adapter and promise</Text>
                </View>
              </TouchableHighlight>
              <TouchableHighlight
                style={styles.buttonContainer}
                onPress={() => openActivityWithName()}>
                <View style={styles.button}>
                  <Text>openActivityWithName</Text>
                </View>
              </TouchableHighlight>
              <TouchableHighlight
                style={styles.buttonContainer}
                onPress={() => openActivityWithNameAndResult()}>
                <View style={styles.button}>
                  <Text>openActivityWithNameAndResult</Text>
                </View>
              </TouchableHighlight>
              <TouchableHighlight
                style={styles.buttonContainer}
                onPress={() => invokeNative()}>
                <View style={styles.button}>
                  <Text>invokeNative</Text>
                </View>
              </TouchableHighlight>
            </View>
          </Section>
          <Section title="Native Components">
            <View style={styles.container} />
          </Section>
          <Section title="See Your Changes" />
          <Section title="Debug" />
          <Section title="Learn More" />
          <LearnMoreLinks />
        </View>
      </ScrollView>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
  },
  highlight: {
    fontWeight: '700',
  },
  buttonContainer: {
    paddingBottom: 10,
  },
  button: {
    alignItems: 'center',
    backgroundColor: '#DDDDDD',
    padding: 10,
  },
  container: {
    flexDirection: 'column',
  },
});

export default App;
