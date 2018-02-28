package com.lzy.libview.activity;

/**
 * 用于Fragment集成实现，当需要右键点击事件的时候，框架中的Activity会把导航栏右键的点击事件分发给Fragment去实现逻辑
 *
 * @author: cyli8
 * @date: 2018/2/28 11:55
 */

public interface IClickRightButtonAble {
    void onClickRightTv();

    void onClickRightBtn();

    CharSequence getRightButtonTv();

    int getRightButtonImgId();
}
