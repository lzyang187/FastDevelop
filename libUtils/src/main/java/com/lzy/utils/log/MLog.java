package com.lzy.utils.log;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;


/**
 * 日志初始化类，基于Logger
 *
 * @author: cyli8
 * @date: 2018/2/11 17:20
 */

public class MLog {
    public static final String DEFAULT_LOG = "lzy";

    public static void initLogger(final boolean logable) {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(0)        // (Optional) Hides internal method calls up to offset. Default 0
//                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag(DEFAULT_LOG)   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return logable;
            }
        });

        FormatStrategy fileformatStrategy = CsvFormatStrategy.newBuilder()
                .tag(DEFAULT_LOG)
                .build();
        Logger.addLogAdapter(new DiskLogAdapter(fileformatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return logable;
            }
        });
    }
}
