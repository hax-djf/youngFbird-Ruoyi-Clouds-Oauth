package com.flybirds.dict.core.util;

import com.flybirds.dict.core.dto.DictDataCacheDTO;
import com.flybirds.dict.core.service.DictDataFrameworkService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 字典工具类
 */
@Slf4j
public class DictFrameworkUtils {

    private static DictDataFrameworkService service;

    public static void init(DictDataFrameworkService service) {
        DictFrameworkUtils.service = service;
        log.info("[init][初始化 DictFrameworkUtils 成功]");
    }

    public static DictDataCacheDTO getDictDataFromCache(String type, String value) {
        return service.parseDictDataFromCacheByValue(type, value);
    }

    public static DictDataCacheDTO parseDictDataFromCache(String type, String label) {
        return service.parseDictDataFromCacheByLabel(type, label);
    }

    public static List<DictDataCacheDTO> listDictDatasFromCache(String type) {
        return service.listDictDatasFromCache(type);
    }

    public static String getDictLabel(String dictType,String dictValue,String separator){
        return service.getDictLabel(dictType, dictValue,separator);
    }

    public static String getDictValue(String dictType,String dictLabel,String separator){
        return service.getDictValue(dictType, dictLabel,separator);
    }

}
