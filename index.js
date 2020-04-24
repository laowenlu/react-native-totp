
import { NativeModules } from 'react-native';

const { RNTotp } = NativeModules;

var NTotp = {
  generateOTP(config, callback) {
      if (RNTotp) {
        if (!config || !(config instanceof Object) || Object.keys(config) === 0 || !config.base32String) {
          console.error('请检查config参数是否正确');
          return;
        }

        RNTotp.generateOTP(config, callback);
      }
  },
};

export default NTotp;
