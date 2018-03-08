package com.lzy.bizcore.helper;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

import com.lzy.bizcore.R;
import com.lzy.utils.StringUtil;
import com.lzy.utils.TimeUtil;

/**
 * 通知管理器
 *
 * @author: cyli8
 * @date: 2018/3/8 09:02
 */

public class NotifiManager {

    private static final String TAG = "NotificationMgr";

    private static final int NOTIFICATION_ID_1 = 1;//消息推送-系统通知
    private static final int NOTIFICATION_ID_2 = 2;//消息推送-系统消息
    public static final int NOTIFICATION_ID_3 = 3;//本地推送

    public static final String PRIMARY_CHANNEL_ID = "default_id";

    private static Notification getCustomNotification(Context context, String ticker, String title,
                                                      String content, PendingIntent pendingIntent, PendingIntent deleteIntent,
                                                      NotificationManager mgr) {
        Notification notification;
        RemoteViews contentView = new RemoteViews(context.getPackageName(),
                R.layout.biz_core_custom_notification_item_layout);
        contentView.setTextViewText(R.id.custom_notification_time, TimeUtil.getSpecialFormatTime("HH:mm", System.currentTimeMillis()));
        contentView.setTextViewText(R.id.custom_notification_title, title);
        contentView.setTextViewText(R.id.custom_notification_content, content);
        if (StringUtil.isTrimEmpty(title)) {
            contentView.setViewVisibility(R.id.custom_notification_title, View.GONE);
        }
        if (StringUtil.isTrimEmpty(content)) {
            contentView.setViewVisibility(R.id.custom_notification_content, View.GONE);
        }
        int smallIcon = R.mipmap.biz_core_icon_small;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            smallIcon = R.mipmap.biz_core_icon_small_alpha;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(PRIMARY_CHANNEL_ID, "消息通知", NotificationManager.IMPORTANCE_DEFAULT);
            mgr.createNotificationChannel(channel);
            notification = new Notification.Builder(context, PRIMARY_CHANNEL_ID)
                    .setSmallIcon(android.R.drawable.stat_notify_chat)
                    .setTicker(ticker)
                    .setWhen(System.currentTimeMillis())
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.biz_core_icon_large))
                    .setContentTitle(title)
                    .setContentText(content)
                    .setContentIntent(pendingIntent)
                    .setDeleteIntent(deleteIntent)
                    .build();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification = new Notification.Builder(context)
                    .setSmallIcon(smallIcon)
                    .setTicker(ticker).setWhen(System.currentTimeMillis())
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.biz_core_icon_large))
                    .setContentTitle(title)
                    .setContentText(content)
                    .setContentIntent(pendingIntent)
                    .setDeleteIntent(deleteIntent)
                    .build();
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setSmallIcon(smallIcon)
                    .setTicker(ticker)
                    .setWhen(System.currentTimeMillis())
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.biz_core_icon_large))
                    .setContentTitle(title)
                    .setContentText(content)
                    .setContentIntent(pendingIntent)
                    .setDeleteIntent(deleteIntent)
                    .setContent(contentView);
            NotificationCompat.BigTextStyle textStyle =
                    new NotificationCompat.BigTextStyle(builder);
            textStyle.bigText(content);
            notification = textStyle.build();
        }
        notification.flags |= Notification.FLAG_AUTO_CANCEL; //自动终止
        notification.defaults |= Notification.DEFAULT_SOUND; //默认声音
        return notification;
    }

    /**
     * 消息推送使用
     */
//    public static boolean startNotify(Context context, PushMessage pushMessage) {
//        if (pushMessage == null) {
//            return false;
//        }
//        PendingIntent intent = pushMessage.getPendingIntent(context);
//        if (null == intent) {
//            return false;
//        }
//        PendingIntent deleteIntent = pushMessage.getDeletePendingIntent(context);
//        NotificationManager mgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        if (mgr == null) {
//            return false;
//        }
//        Notification notification;
//        String showTitle = pushMessage.title;
//        if (showTitle == null || "".equals(showTitle.trim())) {
//            showTitle = pushMessage.content;
//        }
//        notification = getCustomNotification(context, showTitle, showTitle,
//                pushMessage.content, intent, deleteIntent, mgr);
//        if (pushMessage.isSystemNotification()) {
//            mgr.notify(NOTIFICATION_ID_1, notification);
//        } else if (pushMessage.isSystemMessage()) {
//            mgr.notify(NOTIFICATION_ID_2, notification);
//        }
//        return true;
//    }

    /**
     * 本地推送使用
     */
    public static void startNotify(Context context, String title,
                                   String content, PendingIntent pendingIntent) {
        NotificationManager mgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (mgr == null) {
            return;
        }
        Notification notification;
        notification = getCustomNotification(context, title, title,
                content, pendingIntent, null, mgr);
        mgr.notify(NOTIFICATION_ID_3, notification);
    }

    public static void cancelNotify(Context context, int id) {
        NotificationManager mNotifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (mNotifyManager == null) {
            return;
        }
        mNotifyManager.cancel(id);
    }
}
