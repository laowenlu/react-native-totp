package com.reactlibrary.generator;

import android.text.TextUtils;
import android.util.Log;
import java.security.GeneralSecurityException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class TotpUtil {
    private final static int REFLECTIVE_PIN_LENGTH = 9;
    private static int digits;

    /**
     * 生成6位数的手机令牌号
     * @param secret base32编码的密钥
     * @param digit  位数
     * @param period 有效时间, 秒
     * @return
     */
    public static String generateOTP(String secret, int digit, long period) {
        if (TextUtils.isEmpty(secret)) return "";
        digits = digit;

        long seconds = System.currentTimeMillis() / 1000;
        long time;
        if (seconds >= 0) {
            time = seconds / period;
        } else {
            time = (seconds - (period - 1)) / period;
        }

        try {
            return computePin(secret, time,null);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Computes the one-time PIN given the secret key.
     *
     * @author zhangyf
     *
     * @param secret
     *            the secret key
     * @param otp_state
     *            current token state (counter or time-interval)
     * @param challenge
     *            optional challenge bytes to include when computing passcode.
     * @return the PIN
     */
    private static String computePin(String secret, long otp_state, byte[] challenge)
            throws Exception {
        try {
            PasscodeGenerator.Signer signer = getSigningOracle(secret);
            PasscodeGenerator pcg = new PasscodeGenerator(signer,
                    (challenge == null) ? digits : REFLECTIVE_PIN_LENGTH);

            return (challenge == null) ? pcg.generateResponseCode(otp_state)
                    : pcg.generateResponseCode(otp_state, challenge);
        } catch (GeneralSecurityException e) {
            throw new Exception("Crypto failure", e);
        }
    }

    private static PasscodeGenerator.Signer getSigningOracle(String secret) {
        try {
            byte[] keyBytes = decodeKey(secret);
            final Mac mac = Mac.getInstance("HMACSHA1");
            mac.init(new SecretKeySpec(keyBytes, ""));

            // Create a signer object out of the standard Java MAC
            // implementation.
            return new PasscodeGenerator.Signer() {
                @Override
                public byte[] sign(byte[] data) {
                    return mac.doFinal(data);
                }
            };
        } catch (Exception error) {
            Log.e("TotpUtil--", error.getMessage());
        }

        return null;
    }

    private static byte[] decodeKey(String secret) throws Exception {
        return Base32String.decode(secret);
    }
}
