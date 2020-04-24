declare module 'react-native-totp' {

  export default class Totp {
    /**
     * 获取TOTP验证码
     * @param config 参数配置, base32String: base32编码的公钥, digits: 位数,默认6位, period: 有效时间,单位秒, 默认60秒
     * @param callback 结果回调
     */
    static generateOTP(
      config: { base32String: string, digits?: number, period?: number },
      callback: (code: string) => void
    );
  }
}