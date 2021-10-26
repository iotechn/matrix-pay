package com.dobbinsoft.fw.pay.util;

import com.dobbinsoft.fw.pay.anntation.MatrixIgnoreCopy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class MatrixBeanUtils {

    public static final Set<Class> IGNORE_PARAM_LIST = new HashSet();

    static {
        IGNORE_PARAM_LIST.add(Boolean.TYPE);
        IGNORE_PARAM_LIST.add(Byte.TYPE);
        IGNORE_PARAM_LIST.add(Character.TYPE);
        IGNORE_PARAM_LIST.add(Short.TYPE);
        IGNORE_PARAM_LIST.add(Integer.TYPE);
        IGNORE_PARAM_LIST.add(Long.TYPE);
        IGNORE_PARAM_LIST.add(Float.TYPE);
        IGNORE_PARAM_LIST.add(Double.TYPE);
        IGNORE_PARAM_LIST.add(Byte.class);
        IGNORE_PARAM_LIST.add(Character.class);
        IGNORE_PARAM_LIST.add(Short.class);
        IGNORE_PARAM_LIST.add(Integer.class);
        IGNORE_PARAM_LIST.add(Long.class);
        IGNORE_PARAM_LIST.add(String.class);
        IGNORE_PARAM_LIST.add(Float.class);
        IGNORE_PARAM_LIST.add(Double.class);
        IGNORE_PARAM_LIST.add(Boolean.class);
        IGNORE_PARAM_LIST.add(List.class);
    }

    /**
     * 深度拷贝对象 ，并将 wx -> matrix
     * @param source
     * @param target
     */
    public static void copyWxProperties(Object source, Object target) {
        if (source == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (target == null) {
            throw new NullPointerException("Target must not be null");
        }
        if (source instanceof Collection || target instanceof Collection) {
            throw new RuntimeException("Collection not support");
        }
        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();
        List<Field> sourceFields = new ArrayList<>();
        for (Field f : sourceClass.getDeclaredFields()) {
            sourceFields.add(f);
        }
        for (Field f : sourceClass.getSuperclass().getDeclaredFields()) {
            sourceFields.add(f);
        }
        List<Field> targetFields = new ArrayList<>();
        for (Field f : targetClass.getDeclaredFields()) {
            targetFields.add(f);
        }
        for (Field f : targetClass.getSuperclass().getDeclaredFields()) {
            targetFields.add(f);
        }
        Map<String, Field> targetMap = targetFields.stream().collect(Collectors.toMap(Field::getName, v -> v));
        for (Field sourceField : sourceFields) {
            if (sourceField.getAnnotation(MatrixIgnoreCopy.class) != null) {
                continue;
            }
            String name = sourceField.getName();
            Field targetField = targetMap.get(name);
            if (targetField != null) {
                try {
                    Class<?> targetType = targetField.getType();
                    Class<?> sourceType = sourceField.getType();
                    String getterName = getMethodName(name, "get");
                    Method getter = sourceClass.getMethod(getterName);
                    Object res = getter.invoke(source);
                    String setterName = getMethodName(name, "set");
                    Method setter = targetClass.getMethod(setterName, targetType);
                    if (targetType == sourceType) {
                        // 同类型，直接复制
                        setter.invoke(target, res);
                    } else if (!IGNORE_PARAM_LIST.contains(targetType)) {
                        // 自定义参数
                        if (res == null) {
                            // 直接设置空参
                            setter.invoke(target, null);
                        } else {
                            // 创建对象
                            Object o = targetType.newInstance();
                            // 递归赋值子对象
                            copyWxProperties(res, o);
                            setter.invoke(target, o);
                        }
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    public static String getMethodName(String fieldName, String prefix) {
        char[] dst = new char[fieldName.length() + 3];
        prefix.getChars(0, 3, dst, 0);
        fieldName.getChars(0, fieldName.length(), dst, 3);
        if ('a' <= dst[3] && 'z' >= dst[3]) {
            dst[3] = (char)(dst[3] - 32);
        }

        return new String(dst);
    }

}
