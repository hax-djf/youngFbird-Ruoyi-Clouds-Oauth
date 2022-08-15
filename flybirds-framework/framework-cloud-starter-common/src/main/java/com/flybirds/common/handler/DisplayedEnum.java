package com.flybirds.common.handler;

import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * 基于mybatis 通用的枚举传参处理
 *
 * @author :flybirds
 */
public interface DisplayedEnum {

    String DEFAULT_VALUE_NAME = "value";

    String DEFAULT_LABEL_NAME = "label";

    default Integer getValue() {
        Field field = ReflectionUtils.findField(this.getClass(), DEFAULT_VALUE_NAME);
        if (field == null)
            return null;
        try {
            field.setAccessible(true);
            return Integer.parseInt(field.get(this).toString());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @JsonValue
    default String getLabel() {
        Field field = ReflectionUtils.findField(this.getClass(), DEFAULT_LABEL_NAME);
        if (field == null)
            return null;
        try {
            field.setAccessible(true);
            return field.get(this).toString();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    static <T extends Enum<T>> T valueOfEnum(Class<T> enumClass, Integer value) {
        if (value == null)
            throw  new IllegalArgumentException("DisplayedEnum value should not be null");
        if (enumClass.isAssignableFrom(DisplayedEnum.class))
            throw new IllegalArgumentException("illegal DisplayedEnum type");
        T[] enums = enumClass.getEnumConstants();
        for (T t: enums) {
            DisplayedEnum displayedEnum = (DisplayedEnum)t;
            if (displayedEnum.getValue().equals(value))
                return (T) displayedEnum;
        }
        throw new IllegalArgumentException("cannot parse integer: " + value + " to " + enumClass.getName());
    }
}

