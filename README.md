2021.11.23
RN 与 Android交互

1、 RN 调用 Android方法
1)rn端使用NativeModules模块引用Android暴露的模块名，方法名
如 NativeModules.ReactModule.sendDataToNative()
2) 支持的数据类型：Array(ReadableArray)、Object(ReadableMap)、Number(float/double/int)、Boolean、String、function(Callback)。 一般常用应该是Object，便于扩展。
3）如果用js里的fn.apply(null, args),Android不好定义入参。不知道有没有什么方法？
4) 常用方法应该是Promise的形式，不过传入错误数据类型会红屏，除了传入之前校验参数类型合法性，还有没有其他静默处理的方式？？？
5) 关键点应该是数据类型，和异常捕获


2、 Android 调 RN方法

3、 RN 调用 Android UI Component

4、 Android 调用 RN UI Component