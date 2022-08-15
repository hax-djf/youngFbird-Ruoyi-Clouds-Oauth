package com.flybirds.common.util.gen;

/**
 * 代码生成器，类名转换工具
 *
 * @author :flybirds
 */
public class GenConvertUtils {

    public static String getClassName(String modelName) {

        char[] cs = changeToJavaFiled(modelName).toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

    public static String changeToJavaFiled(String field) {
        String[] fields = field.split("_");
        StringBuilder builder = new StringBuilder(fields[0]);
        for (int i = 1; i < fields.length; i++) {
            char[] cs = fields[i].toCharArray();
            cs[0] -= 32;
            builder.append(String.valueOf(cs));
        }
        return builder.toString();
    }
}
