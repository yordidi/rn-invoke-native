### 2021.11.23
RN 与 Android交互

1、 RN 调用 Android方法
1)rn端使用NativeModules模块引用Android暴露的模块名，方法名
如 NativeModules.ReactModule.sendDataToNative()
2) 支持的数据类型：Array(ReadableArray)、Object(ReadableMap)、Number(float/double/int)、Boolean、String、function(Callback)。 一般常用应该是Object，便于扩展。
3）如果用js里的fn.apply(null, args),Android不好定义入参。不知道有没有什么方法？
4) 常用方法应该是Promise的形式，不过传入错误数据类型会红屏，除了传入之前校验参数类型合法性，还有没有其他静默处理的方式？？？
5) 关键点应该是数据类型，和异常捕获
6) Native给RN传数据。Native传递数据给RN倒不用像RN给Native那样谨慎，因为JS的回调函数不必限定参数类型，而且参数个数也可以动态的。
例如通过(err, data, ...args)的args接收额外的参数。由于Promise.resolve只能接收一个参数，Promise形式并不能无限个参数。但是
它们都不限定参数类型，因此比Android语法宽松了很多。
### 2021.11.24
7）因为Android语法限定参数类型，而这种错误又不能通过try/catch静默处理掉，一旦传递错误的参数类型给Android就会红屏，因为调用Android
之前，RN端必须检测参数类型，参数不合法不能调用。所以RN端最好去封装这个功能。参见androidMethodRegister.js，此时RN调用Android方法
应该封装完成了。而Android侧，我觉得没必要封装了，官方封装的已经可以了。
8) 完美适配方案参见androidMethodRegister.js。Native端Array、Object类型的处理也OK了。一般给RN回传的数据封装成WritableArray、WritableMap，再通过Promise.resolve传递回去即可。
9) 跨语言通信，只能通过约定。例如可以暴露一个常量进行约定。所以没办法利用一个根本上杜绝参数类型错误，就像前后端通信通过约定content-type一样。对于rn与android，最好是只有ReadableMap，即json进行通信。
10）不知道rn的Error能不能捕获到这种错误？？？
### 2021.11.25
11) RN打开一个Android Activity。包括有返回值和无返回值的。以及各种数据类型
12） Java 字符串比较，map遍历、删除
13） Android 兼容 RN   
The minCompileSdk (31) specified in a dependency’s AAR metadata is greater than this module’s compileSdkVersion ：https://exerror.com/the-mincompilesdk-31-specified-in-a-dependencys-aar-metadata-is-greater-than-this-modules-compilesdkversion/
14）RN如果可以访问原生代码的话，可以实现更高的复用性，以及做一些RN做不到的事情，如多线程图片处理、访问数据库等。
15）RN bridge是异步的
16）RN监听activity生命周期 　模块实现LifecycleEventListener，在模块的构造器中注册。因为以上生命周期可能执行多次，所以不要把JS的回调函数放到里面来执行，因为JS传递进来的回调函数只能执行一次，可以在里面往JS传递事件。监听的应该是自己的Activity的生命周期

2、 Android 调 RN方法
1）通过一个module调用emit(eventName, params)调用rn的方法
2）通过一个常量来约定event name
3) 如果rn端没有监听相关event呢？
4）回传数据的形式倒不必担心
5) 不过Android在什么情况下会调用RN，给RN传数据呢？例如监听Activity生命周期钩子

### 2021.11.26
1）Android Activity 打开一个 rn页面

3、 RN 调用 Android UI Component

4、 Android 调用 RN UI Component