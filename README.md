# LogSwitch [![](https://jitpack.io/v/DonaldDu/LogSwitch.svg)](https://jitpack.io/#DonaldDu/LogSwitch)


```
//useage
UMConfigure.setLogEnabled(LogSwitch.isLogOpen(this))//Java
UMConfigure.setLogEnabled(isLogOpen)//Kotlin

dependencies {
    //app module
    debugImplementation "com.github.DonaldDu.LogSwitch:LIB:$logSwitchV"
    releaseImplementation 'com.didichuxing.doraemonkit:doraemonkit-no-op:1.1.8'

    //add below to multiple module's base
    api "com.github.DonaldDu.LogSwitch:BASE:$logSwitchV"
}
  
```
