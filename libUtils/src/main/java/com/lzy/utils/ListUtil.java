package com.lzy.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: cyli8
 * @date: 2018/2/23 09:15
 */

public class ListUtil {
    public static <T> int size(List<T> list) {
        return null == list ? 0 : list.size();
    }

    public static <T> boolean isIndexValid(List<T> list, int index) {
        if (null == list) {
            return false;
        }
        return index >= 0 && index < list.size();
    }

    public static int size(List<?>... lists) {
        int count = 0;
        for (List<?> l : lists) {
            count += size(l);
        }
        return count;
    }

    public static <T> int size(T... list) {
        return null == list ? 0 : list.length;
    }

    public static <T> boolean isEmpty(List<T> list) {
        if (null == list) {
            return true;
        }
        return list.isEmpty();
    }

    public static boolean isEmpty(List<?>... lists) {
        if (null == lists) {
            return true;
        }
        for (List<?> l : lists) {
            if (isNotEmpty(l)) {
                return false;
            }
        }
        return true;
    }

    public static <T> boolean isEmpty(T[] list) {
        if (null == list) {
            return true;
        }
        return list.length == 0;
    }

    public static <T> boolean isNotEmpty(List<T> list) {
        return !isEmpty(list);
    }

    public static <T> boolean isNotEmpty(T[] list) {
        return !isEmpty(list);
    }

    public static boolean isNotEmpty(Map map) {
        if (null != map && map.size() > 0) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(Set map) {
        if (null != map && map.size() > 0) {
            return true;
        }
        return false;
    }

    public static <T> boolean isEmpty2(T... list) {
        return list == null || list.length == 0;
    }

    public static <T> boolean isNotEmpty2(T... list) {
        return !isEmpty2(list);
    }

    public static <T> T getItem(List<T> list, int pos) {
        if (null == list) {
            return null;
        }
        int size = list.size();
        if (pos < 0 || pos >= size) {
            return null;
        }
        return list.get(pos);
    }

    public static <T> boolean contains(T[] list, T item) {
        for (T i : list) {
            if (i == item || i.equals(item)) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean indexIn(List<T> list, int index) {
        if (null == list) {
            return false;
        }
        return index >= 0 && index < list.size();
    }

    public static <T> List<T> copy(List<T> arr) {
        int size = ListUtil.size(arr);
        ArrayList<T> ret = new ArrayList<>(size);
        if (size > 0) {
            ret.addAll(arr);
        }
        return ret;
    }

    public static <T> List<T> newList(List<T> list) {
        if (null == list) {
            return new ArrayList<T>();
        } else {
            list.clear();
            return list;
        }
    }
}
