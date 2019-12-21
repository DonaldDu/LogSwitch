# LogSwitch [![](https://jitpack.io/v/DonaldDu/LogSwitch.svg)](https://jitpack.io/#DonaldDu/LogSwitch)


```
//useage
UMConfigure.setLogEnabled(LogSwitch.isLogOpen(this))//Java
UMConfigure.setLogEnabled(isLogOpen)//Kotlin

dependencies {
    implementation 'com.github.DonaldDu:ShakeDetector:0.9.0'
    debugImplementation "com.github.DonaldDu.LogSwitch:LIB:$logSwitchV"
    releaseImplementation 'com.didichuxing.doraemonkit:doraemonkit-no-op:1.1.8'
    
    api "com.github.DonaldDu.LogSwitch:BASE:$logSwitchV"
}
  
```
