
package com.reactlibrary;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;
import com.reactlibrary.generator.TotpUtil;

public class RNTotpModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public final static long defaultPeriod = 60;  // 有效时间, 秒
  public final static int defaultDigits = 6;    // 位数

  public RNTotpModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNTotp";
  }

  @ReactMethod
  public void generateOTP(ReadableMap config, Callback callback) {
    if (null == config || !config.hasKey("base32String")) {
      if (null != callback) callback.invoke("");
      return;
    }

    String code = TotpUtil.generateOTP(
            config.getString("base32String"),
            config.hasKey("digits") ? config.getInt("digits") : defaultDigits,
            config.hasKey("period") ? config.getInt("period") : defaultPeriod
    );

    if (null != callback) callback.invoke(code);
  }
}