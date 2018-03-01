package com.lzy.libview.activity;

import android.content.Intent;

/**
 * 用于Activity发起StartActivityyForResult 简化Activity中的onActivityResult的实现函数量，
 * 清晰的把每一个requestCode指向对应的实际逻辑
 *
 * @author: cyli8
 * @date: 2018/2/28 10:55
 */

public interface ActivityResultTask {
    void execute(int resultCode, Intent data);
}
