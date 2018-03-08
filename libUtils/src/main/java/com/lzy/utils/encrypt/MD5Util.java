package com.lzy.utils.encrypt;

import android.text.TextUtils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * md5加密工具类
 *
 * @author: cyli8
 * @date: 2018/2/11 17:17
 */

public class MD5Util {
    private static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f'};

    public final static String md5Encode(final String s) {
        if (TextUtils.isEmpty(s)) {
            return "";
        }
        try {
            final byte[] strTemp = s.getBytes();
            final MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            final byte[] md = mdTemp.digest();
            final int j = md.length;
            final char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                final byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    public final static String md5Encode(final byte[] md) {
        try {
            final int j = md.length;
            final char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                final byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    private static final String toHexString(final byte[] b) {
        final StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            final byte byte0 = b[i];
            sb.append(hexDigits[byte0 >>> 4 & 0xf]);
            sb.append(hexDigits[byte0 & 0x0f]);
        }
        return sb.toString();
    }

    /**
     * 求一个文件的MD5签名
     *
     * @return
     */
    public final static String md5EncodeFile(final String fileName) {
        final byte[] buffer = new byte[1024];
        int bytes;
        MessageDigest md5;
        try {
            final InputStream is = new FileInputStream(fileName);
            md5 = MessageDigest.getInstance("MD5");
            while ((bytes = is.read(buffer)) > 0) {
                md5.update(buffer, 0, bytes);
            }
            is.close();
            return toHexString(md5.digest());
        } catch (Exception e) {
            return null;
        }
    }
}
