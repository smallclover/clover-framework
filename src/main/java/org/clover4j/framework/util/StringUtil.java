package org.clover4j.framework.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 * @author smallclover
 * @create 2017-01-03
 * @since 1.0.0
 */
public class StringUtil {

    /**
     * 字符串分隔符
     */
    public static final String SEPARATOR = String.valueOf((char)29);//? 分组符


    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        if (str != null){//这一步似乎与StringUtils.isEmpty重复
            str = str.trim();
        }

        return StringUtils.isEmpty(str);
    }

    /**
     * 判断字符串是否非空
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str){
            return !isEmpty(str);
    }

    /**
     * 分隔固定格式的字符串
     * @param str
     * @param separator
     * @return
     */
    public static String[] splitString(String str, String separator){
        return StringUtils.splitByWholeSeparator(str, separator);
    }

}
