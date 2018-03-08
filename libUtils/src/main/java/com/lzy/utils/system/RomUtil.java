package com.lzy.utils.system;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 手机系统类型工具类
 *
 * @author: cyli8
 * @date: 2018/3/2 10:45
 */

public class RomUtil {
    public static final String BRAND_XIAOMI = "xiaomi";
    public static final String BRAND_HUAWEI = "huawei";
    public static final String BRAND_OPPO = "oppo";
    public static final String BRAND_VIVO = "vivo";
    public static final String BRAND_SUMSUNG = "samsung";
    public static final String BRAND_COOLPAD = "coolpad";
    public static final String BRAND_CUHIZI = "smartisan";
    public static final String BRAND_MEIZU = "meizu";
    public static final String BRAND_LENOVO = "lenovo";
    public static final String BRAND_GIONEE = "GiONEE";
    public static final String BRAND_HONOR = "Honor";
    public static final String BRAND_LETV = "Letv";
    public static final String BRAND_QIKU = "QiKu";
    public static final String BRAND_SPRD = "SPRD";
    public static final String BRAND_LEECO = "LeEco";
    public static final String BRAND_NUBIA = "nubia";
    public static final String BRAND_OTHER = "other";
    private static String mPhoneType = null;


    /**
     * 获取手机类型
     *
     * @return
     */
    public static String getPhoneType() {
        if (null != mPhoneType) {
            return mPhoneType;
        }
        String brand = Build.BRAND;
        String user = Build.USER;
        String host = Build.HOST;
        String product = Build.PRODUCT;
        Log.e("cyli8", "brand = " + brand + ", user = " + user + ", host = " + host + ",product = " + product);

        if (BRAND_XIAOMI.equalsIgnoreCase(brand) || BRAND_XIAOMI.equalsIgnoreCase(user)) {
            mPhoneType = BRAND_XIAOMI;
        }
        if (BRAND_HUAWEI.equalsIgnoreCase(brand) || BRAND_HUAWEI.equalsIgnoreCase(user) || BRAND_HONOR.equalsIgnoreCase(brand)) {
            mPhoneType = BRAND_HUAWEI;
        }
        if (BRAND_OPPO.equalsIgnoreCase(brand) || BRAND_OPPO.equalsIgnoreCase(user)) {
            mPhoneType = BRAND_OPPO;
        }
        if (BRAND_VIVO.equalsIgnoreCase(brand) || BRAND_VIVO.equalsIgnoreCase(user)) {
            mPhoneType = BRAND_VIVO;
        }
        if (BRAND_SUMSUNG.equalsIgnoreCase(brand) || BRAND_SUMSUNG.equalsIgnoreCase(user)) {
            mPhoneType = BRAND_SUMSUNG;
        } else if (BRAND_COOLPAD.equalsIgnoreCase(brand) || BRAND_COOLPAD.equalsIgnoreCase(user)) {
            mPhoneType = BRAND_COOLPAD;
        } else if (BRAND_CUHIZI.equalsIgnoreCase(brand) || BRAND_CUHIZI.equalsIgnoreCase(user)) {
            mPhoneType = BRAND_CUHIZI;
        } else if (BRAND_MEIZU.equalsIgnoreCase(brand) || BRAND_MEIZU.equalsIgnoreCase(user)) {
            mPhoneType = BRAND_MEIZU;
        } else if (BRAND_LENOVO.equalsIgnoreCase(brand) || BRAND_LENOVO.equalsIgnoreCase(user)) {
            mPhoneType = BRAND_LENOVO;
        } else if (BRAND_GIONEE.equalsIgnoreCase(brand) || BRAND_GIONEE.equalsIgnoreCase(user)) {
            mPhoneType = BRAND_GIONEE;
        } else if (BRAND_LETV.equalsIgnoreCase(brand)) {
            mPhoneType = BRAND_LETV;
        } else if (BRAND_QIKU.equalsIgnoreCase(brand)) {
            mPhoneType = BRAND_QIKU;
        } else if (BRAND_SPRD.equalsIgnoreCase(brand)) {
            mPhoneType = BRAND_SPRD;
        } else if (BRAND_LEECO.equalsIgnoreCase(brand)) {
            mPhoneType = BRAND_LEECO;
        } else if (BRAND_NUBIA.equalsIgnoreCase(brand)) {
            mPhoneType = BRAND_NUBIA;
        }

        if (null == mPhoneType) {
            if (null == host) {
                host = "";
            } else {
                host = host.toLowerCase();
            }
            if (null == product) {
                product = "";
            } else {
                product = product.toLowerCase();
            }
            if (BRAND_XIAOMI.contains(host) || BRAND_XIAOMI.contains(product)) {
                mPhoneType = BRAND_XIAOMI;
            }
            if (BRAND_HUAWEI.contains(host) || BRAND_HUAWEI.contains(product)) {
                mPhoneType = BRAND_HUAWEI;
            }
            if (BRAND_OPPO.contains(host) || BRAND_OPPO.contains(product)) {
                mPhoneType = BRAND_OPPO;
            }
            if (BRAND_VIVO.contains(host) || BRAND_VIVO.contains(product)) {
                mPhoneType = BRAND_VIVO;
            }
            if (BRAND_SUMSUNG.contains(host) || BRAND_SUMSUNG.contains(product)) {
                mPhoneType = BRAND_SUMSUNG;
            }
            if (BRAND_COOLPAD.contains(host) || BRAND_COOLPAD.contains(product)) {
                mPhoneType = BRAND_COOLPAD;
            }
            if (BRAND_CUHIZI.contains(host) || BRAND_CUHIZI.contains(product)) {
                mPhoneType = BRAND_CUHIZI;
            }
            if (BRAND_MEIZU.contains(host) || BRAND_MEIZU.contains(product)) {
                mPhoneType = BRAND_MEIZU;
            }
            if (BRAND_LENOVO.contains(host) || BRAND_LENOVO.contains(product)) {
                mPhoneType = BRAND_LENOVO;
            }
            if (BRAND_GIONEE.contains(host) || BRAND_GIONEE.contains(product)) {
                mPhoneType = BRAND_GIONEE;
            } else if (BRAND_LETV.contains(host) || BRAND_LETV.contains(product)) {
                mPhoneType = BRAND_LETV;
            } else if (BRAND_QIKU.contains(host) || BRAND_QIKU.contains(product)) {
                mPhoneType = BRAND_QIKU;
            } else if (BRAND_SPRD.contains(host) || BRAND_SPRD.contains(product)) {
                mPhoneType = BRAND_SPRD;
            } else if (BRAND_LEECO.contains(host) || BRAND_LEECO.contains(product)) {
                mPhoneType = BRAND_LEECO;
            } else if (BRAND_NUBIA.contains(host) || BRAND_NUBIA.contains(product)) {
                mPhoneType = BRAND_NUBIA;
            }
        }
        if (null == mPhoneType) {
            mPhoneType = BRAND_OTHER;
        }

        return mPhoneType;
    }

    final static String MIUI_V5 = "v5";
    final static String MIUI_V6 = "v6";
    final static String MIUI_V7 = "v7";
    final static String MIUI_V8 = "v8";
    final static String MIUI_V9 = "v9";

    /**
     * 是否是miui5
     *
     * @param context
     * @return
     */
    public static boolean isMIUI5(Context context) {
        String result = "";
        try {
            result = BuildProperties.newInstance().getProperty("ro.miui.ui.version.name");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return MIUI_V5.equalsIgnoreCase(result);
    }

    /**
     * 是否是miui6及以上
     *
     * @param context
     * @return
     */
    public static boolean isMIUI6OrHigher(Context context) {
        String result = "";
        try {
            result = BuildProperties.newInstance().getProperty("ro.miui.ui.version.name");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return MIUI_V6.equalsIgnoreCase(result) || MIUI_V7.equalsIgnoreCase(result) || MIUI_V8.equalsIgnoreCase(result) || MIUI_V9.equalsIgnoreCase(result);
    }


    /**
     * 判断vivo手机的系统版本：
     * 是否低于Funtouch OS_2.0
     */
    public static boolean isFuntouch2Lower(Context context) {
        String result = "";
        try {
            result = BuildProperties.newInstance().getProperty("ro.vivo.os.build.display.id");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (result != null) && (result.compareTo("Funtouch OS_2.0") < 0);
    }

    /**
     * vivo手机版本大于等于funtouch os_2.0,小于3.0
     */
    public static boolean isFuntouchBetween2And3(Context context) {
        String result = "";
        try {
            result = BuildProperties.newInstance().getProperty("ro.vivo.os.build.display.id");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (result != null) && ((result.compareTo("Funtouch OS_2.6") == 0)
                || (result.compareTo("Funtouch OS_2.0") >= 0)
                && (result.compareTo("Funtouch OS_3.0") < 0));
    }

    /**
     * vivo手机系统版本为Funtouch os_3.0以上
     */
    public static boolean isFuntouch3OrHigher(Context context) {
        String result = "";
        try {
            result = BuildProperties.newInstance().getProperty("ro.vivo.os.build.display.id");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (result != null) && ((result.compareTo("Funtouch OS_3.0 Lite") == 0));
    }


    /**
     * 判断金立手机系统版本
     * 系统版本是否为amigo4.0.0S.0以上
     */
    public static boolean isAmigo4OrHigher(Context context) {
        String result = "";
        try {
            result = BuildProperties.newInstance().getProperty("ro.build.display.id");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (result != null) && (result.compareTo("amigo4.0.0S.0") >= 0);
    }

    /**
     * 金立手机系统版本是否为amigo3.0-4.0
     */
    public static boolean isAmigoBetween3And4(Context context) {
        String result = "";
        try {
            result = BuildProperties.newInstance().getProperty("ro.build.display.id");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (result != null) && ((result.compareTo("amigo3.1.2") == 0) ||
                ((result.compareTo("amigo3.0.0") > 0)
                        && (result.compareTo("amigo4.0.0") < 0)));
    }


    /**
     * 对小辣椒SPRD的手机进行系统版本分离
     * 判断手机系统型号是否大于yunos3.0.0
     */
    public static boolean isYunOs3OrHigher(Context context) {
        String result = "";
        try {
            result = BuildProperties.newInstance().getProperty("ro.yunos.version");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (result != null) && (result.compareTo("3.2.0") == 0);
    }

    /**
     * 对乐视LeEco的系统版本进行判断
     *
     * @param context
     * @return
     */
    public static boolean isLeShiBetween5And6(Context context) {
        String result = "";
        try {
            result = BuildProperties.newInstance().getProperty("ro.letv.release.version");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (result != null) && (result.compareTo("5.9.026S") == 0);
    }

    /**
     * 对华为手机进行版本分离
     * emotion ui 5.0以上的华为手机
     */
    public static boolean isHuaWei5OrHigher(Context context) {
        String result = "";
        try {
            result = getHuaWeiVersion();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (result != null) && ((result.compareTo("EmotionUI_5.1") == 0) || (result.compareTo("EmotionUI_5.0") == 0));
    }

    /**
     * 华为emotionui 4.0和5.0之间的版本
     */
    public static boolean isHuaWeiBetween4And5(Context context) {
        String result = "";
        try {
            result = getHuaWeiVersion();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (result != null) && ((result.compareTo("EmotionUI_4.1") == 0) ||
                (result.compareTo("EmotionUI_4.0") >= 0) && (result.compareTo("EmotionUI_5.0") < 0));
    }

    /**
     * 华为emotion4.0以下的手机进行版本分离
     */
    public static boolean isHuaWei4Lower(Context context) {
        String result = "";
        try {
            result = getHuaWeiVersion();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (result != null) && ((result.compareTo("EmotionUI_3.0") == 0) ||
                result.compareTo("EmotionUI_4.0") < 0);
    }


    /**
     * 对于华为手机无法使用BuildProperties.newInstance().getProperty("ro.build.version.emui"）获取
     * 手机系统的版本，需要实现函数实现华为手机系统版本的获取。（荣耀9）
     */
    public static String getHuaWeiVersion() {
        InputStream is;
        Process p;
        try {
            p = Runtime.getRuntime().exec("getprop ro.build.version.emui");
            is = p.getInputStream();
            BufferedReader bfr = new BufferedReader(new InputStreamReader(is));
            String line = "";
            StringBuilder sb = new StringBuilder();
            while ((line = bfr.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 分离出基于android7.0的三星手机版本
     */
    public static boolean isSamsung7OrHigher(Context context) {
        String result = "";
        try {
            result = BuildProperties.newInstance().getProperty("ro.build.version.release");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (result != null) && ((result.compareTo("7.0") == 0) || (result.compareTo("7.0") > 0 && result.compareTo("8.0") < 0));
    }

    /**
     * 对魅族手机进行版本分离
     * flyme6.0以上
     */
    public static boolean isMeiZu6OrHigher(Context context) {
        String result = "";
        try {
            result = BuildProperties.newInstance().getProperty("ro.build.display.id");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (result != null) && (result.compareTo("Flyme 6.1.0.0A") == 0);
    }

    /**
     * Oppo手机
     * ColorOS3.0.0
     */
    public static boolean isOppo3OrHigher(Context context) {
        String result = "";
        try {
            result = BuildProperties.newInstance().getProperty("ro.rom.different.version");
            if (result == null) {
                result = BuildProperties.newInstance().getProperty("ro.build.version.opporom");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (result != null) && (("ColorOS3.0.0").compareTo(result) == 0 || ("V3.0").compareTo(result) == 0);
    }

    /**
     * 努比亚手机的版本分离
     * v2.0-3.0
     * 2017/8/22:nubiaUI V2.5.1版本
     */
    public static boolean isNubiaBetween2And3(Context context) {
        String result = "";
        try {
            result = BuildProperties.newInstance().getProperty("ro.build.rom.id");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (result != null) && (result.compareTo("V2.5.1") == 0);
    }
}
