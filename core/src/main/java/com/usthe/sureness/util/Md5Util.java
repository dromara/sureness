package com.usthe.sureness.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * md5 util
 * @author tomsun28
 * @date 20:48 2018/2/27
 */
public class Md5Util {

    private static final Logger logger = LoggerFactory.getLogger(Md5Util.class);

    public static String md5(String content) {
        // Characters used for encryption
        char[] md5String = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        try {
            byte[] byteInput = content.getBytes(StandardCharsets.UTF_8);
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(byteInput);
            // Perform hash calculation to obtain ciphertext
            byte[] md = mdInst.digest();

            //Convert ciphertext into hexadecimal string form
            int j = md.length;
            char[] str = new char[j*2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = md5String[byte0 >>> 4 & 0xf];
                str[k++] = md5String[byte0 & 0xf];
            }
            return new String(str);
        }catch (Exception e) {
            logger.warn(e.getMessage(), e);
            return null;
        }
    }
}
