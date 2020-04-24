
# react-native-totp
由谷歌TOTP两步认证动态验证码生成 [google-authenticator](https://github.com/google/google-authenticator) 封装的 react-native 插件, 支持 Android 与 iOS

## 安装
react-native版本 < 0.60

1. `$ npm install react-native-totp --save`
2. `$ react-native link react-native-totp`

react-native版本 >= 0.60

1. `$ npm install react-native-totp --save`
2. `cd ios && pod install`

## 使用

### options

| 参数 | 类型 | 默认 | 必须 | 详情 |
|:-----|:----|:----|:-----|:---:|
| base32String | string |  | 是 | base32编码的字符串, 需与服务器端一致 |
| digits | number | 6 | 否 | 验证码位数, 默认6位, 需与服务器端一致 |
| period | number | 60 | 否 | 有效时间, 单位秒, 默认60秒, 需与服务器端一致 |

```javascript
import RNTotp from 'react-native-totp';

RNTotp.generateOTP({
  base32String: 'R35AQPURMAHIO324',
}, (code) => {
  console.log('验证码是: ', code);
  alert(code);
});
```