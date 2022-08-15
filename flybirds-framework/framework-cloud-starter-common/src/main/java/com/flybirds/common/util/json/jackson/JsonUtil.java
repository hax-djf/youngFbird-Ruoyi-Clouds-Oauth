package com.flybirds.common.util.json.jackson;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * jackjson 工具类
 *
 * @author :flybirds
 */
public class JsonUtil {

    private final static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static ObjectMapper objectMapper = new ObjectMapper();


    /**
     * 初始化 objectMapper 属性 jackson方式
     * <p>
     * 通过这样的方式，使用 Spring 创建的 ObjectMapper Bean
     *
     * 出现无对象无法序列化的情况: https://www.cnblogs.com/charlypage/p/9574452.html
     * @param objectMapper ObjectMapper 对象
     */

    public static void init(ObjectMapper objectMapper) {
        JsonUtil.objectMapper = objectMapper;
    }

    public static String toJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T parseObject(String text, Class<T> clazz) {
        if (StrUtil.isEmpty(text)) {
            return null;
        }
        try {
            return objectMapper.readValue(text, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T parseObject(byte[] bytes, Class<T> clazz) {
        if (ArrayUtil.isEmpty(bytes)) {
            return null;
        }
        try {
            return objectMapper.readValue(bytes, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T parseObject(String text, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(text, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        if (StrUtil.isEmpty(text)) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(text, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * json字符串转换为map
     */
    public static <T> Map<String, Object> jsonToMap(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            return mapper.readValue(jsonString, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * json字符串转换为map
     */
    public static <T> Map<String, T> jsonToMap(String jsonString, Class<T> clazz) throws Exception {
        Map<String, Map<String, Object>> map = (Map<String, Map<String, Object>>) objectMapper.readValue(jsonString, new TypeReference<Map<String, T>>() {
        });
        Map<String, T> result = new HashMap<String, T>();
        for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
            result.put(entry.getKey(), mapToObj(entry.getValue(), clazz));
        }
        return result;
    }

    public static <T> JsonNode jsonToJsonNode(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            return mapper.readValue(jsonString, JsonNode.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }

    }
    /**
     * map 转json
     *
     * @param map
     * @return
     */
    public static String mapToJson(Map map) {
        try {
            return objectMapper.writeValueAsString(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * map  转JavaBean
     */
    public static <T> T objToObj(Object obj, Class<T> clazz) {
        return objectMapper.convertValue(obj, clazz);
    }

    /**
     * 把json解析成map，如果map内部的value存在jsonString，继续解析
     *
     * @param json
     * @param mapper
     * @return
     * @throws Exception
     */
    private static Map<String, Object> jsonToMapRecursion(String json, ObjectMapper mapper) throws Exception {
        if (json == null) {
            return null;
        }

        Map<String, Object> map = mapper.readValue(json, Map.class);

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object obj = entry.getValue();
            if (obj != null && obj instanceof String) {
                String str = ((String) obj);

                if (str.startsWith("[")) {
                    List<?> list = jsonToListRecursion(str, mapper);
                    map.put(entry.getKey(), list);
                } else if (str.startsWith("{")) {
                    Map<String, Object> mapRecursion = jsonToMapRecursion(str, mapper);
                    map.put(entry.getKey(), mapRecursion);
                }
            }
        }

        return map;
    }

    /**
     * 与javaBean json数组字符串转换为列表
     */
    public static <T> List<T> jsonToList(String jsonArrayStr, Class<T> clazz) {

        JavaType javaType = getCollectionType(ArrayList.class, clazz);
        List<T> lst = null;
        try {
            lst = (List<T>) objectMapper.readValue(jsonArrayStr, javaType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return lst;
    }


    /**
     * 获取泛型的Collection Type
     *
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类
     * @return JavaType Java类型
     * @since 1.0
     */
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }


    /**
     * map  转JavaBean
     */
    public static <T> T mapToObj(Map map, Class<T> clazz) {
        return objectMapper.convertValue(map, clazz);
    }

    /**
     * 深度转换json成map
     *
     * @param json
     * @return
     */
    public static Map<String, Object> jsonToMapDeeply(String json) throws Exception {
        return jsonToMapRecursion(json, objectMapper);
    }

    /**
     * 把json解析成list，如果list内部的元素存在jsonString，继续解析
     *
     * @param json
     * @param mapper 解析工具
     * @return
     * @throws Exception
     */
    private static List<Object> jsonToListRecursion(String json, ObjectMapper mapper) throws Exception {
        if (json == null) {
            return null;
        }

        List<Object> list = mapper.readValue(json, List.class);

        for (Object obj : list) {
            if (obj != null && obj instanceof String) {
                String str = (String) obj;
                if (str.startsWith("[")) {
                    obj = jsonToListRecursion(str, mapper);
                } else if (obj.toString().startsWith("{")) {
                    obj = jsonToMapRecursion(str, mapper);
                }
            }
        }

        return list;
    }
}

