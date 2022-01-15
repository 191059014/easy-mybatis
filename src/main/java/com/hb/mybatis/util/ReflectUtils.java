package com.hb.mybatis.util;

import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 反射工具类
 *
 * @version v0.1, 2022/1/13 23:17, create by huangbiao.
 */
public class ReflectUtils {

    /**
     * 类字段缓存
     */
    private final static Map<Class<?>, List<Field>> CLASS_FIELDS = new ConcurrentHashMap<>();

    /**
     * 从缓存中获取所有的字段
     *
     * @param targetClass 目标类
     * @return 字段集合
     */
    public static List<Field> getAllFields(Class<?> targetClass) {
        List<Field> allFields = null;
        // 从缓存中获取
        allFields = CLASS_FIELDS.get(targetClass);
        if (!CollectionUtils.isEmpty(allFields)) {
            return allFields;
        }
        allFields = getAllFieldsRecursive(targetClass);
        // 放入缓存
        CLASS_FIELDS.put(targetClass, allFields);
        return allFields;
    }

    /**
     * 递归获取所有的字段
     *
     * @param targetClass 目标类
     * @return 字段集合
     */
    private static <T> List<Field> getAllFieldsRecursive(Class<T> targetClass) {
        // 自身类的属性
        Field[] selfClassFields = targetClass.getDeclaredFields();
        List<Field> allFields = new ArrayList<>(Arrays.asList(selfClassFields));
        // 递归获取父类的属性
        if (targetClass.getSuperclass() != Object.class) {
            List<Field> parentClassFields = getAllFieldsRecursive(targetClass.getSuperclass());
            allFields.addAll(parentClassFields);
        }
        // 过滤掉static和final修饰的属性
        Predicate<Field> notStaticOrFinalPredicate = field -> {
            int fieldModifiers = field.getModifiers();
            return !Modifier.isStatic(fieldModifiers) && !Modifier.isFinal(fieldModifiers);
        };
        return allFields.stream().filter(notStaticOrFinalPredicate).collect(Collectors.toList());
    }

    /**
     * 把bean转换为map
     *
     * @param obj 对象
     * @return map集合
     */
    public static Map<String, Object> getPropertyMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if (obj == null) {
            return map;
        }
        List<Field> allFields = getAllFields(obj.getClass());
        try {
            for (Field field : allFields) {
                field.setAccessible(true);
                Object value = field.get(obj);
                map.put(field.getName(), value);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    /**
     * 把map转换为bean
     *
     * @param propertyMap 属性集合
     * @param targetClass 目标bean的class
     * @return bean对象
     */
    public static <T> T map2Bean(Map<String, Object> propertyMap, Class<T> targetClass) {
        if (CollectionUtils.isEmpty(propertyMap)) {
            return null;
        }
        List<Field> allFields = getAllFields(targetClass);
        T bean = null;
        try {
            bean = targetClass.newInstance();
            for (Map.Entry<String, Object> entry : propertyMap.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                Optional<Field> optional = allFields.stream().filter(field -> field.getName().equals(key)).findFirst();
                if (optional.isPresent()) {
                    Field field = optional.get();
                    field.setAccessible(true);
                    field.set(bean, value);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return bean;
    }

    /**
     * 把多个map转换为多个bean
     *
     * @param propertyMapList 属性集合
     * @param targetClass     目标bean的class
     * @return bean对象列表
     */
    public static <T> List<T> mapList2BeanList(List<Map<String, Object>> propertyMapList, Class<T> targetClass) {
        List<T> beanList = new ArrayList<>();
        if (CollectionUtils.isEmpty(propertyMapList)) {
            return beanList;
        }
        propertyMapList.forEach(map -> beanList.add(map2Bean(map, targetClass)));
        return beanList;
    }

}

    