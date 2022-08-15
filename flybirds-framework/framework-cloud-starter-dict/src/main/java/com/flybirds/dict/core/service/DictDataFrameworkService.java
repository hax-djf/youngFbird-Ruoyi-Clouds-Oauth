package com.flybirds.dict.core.service;


import com.flybirds.dict.core.dto.DictDataCacheDTO;
import java.util.List;

public interface DictDataFrameworkService {

    /**
     * 获得指定的字典数据，从缓存中
     *
     * @param type 字典类型
     * @param value 字典数据值
     * @return 字典数据
     */
    DictDataCacheDTO parseDictDataFromCacheByValue(String type, String value);

    /**
     * 解析获得指定的字典数据，从缓存中
     *
     * @param type 字典类型
     * @param label 字典数据标签
     * @return 字典数据
     */
    DictDataCacheDTO parseDictDataFromCacheByLabel(String type, String label);

    /**
     * 获得指定类型的字典数据，从缓存中
     *
     * @param type 字典类型
     * @return 字典数据列表
     */
    List<DictDataCacheDTO> listDictDatasFromCache(String type);

    /**
     * 根据字典类型和字典值获取字典标签
     *
     * @param dictType 字典类型
     * @param dictValue 字典值
     * @param separator 分隔符
     * @return 字典标签
     */
    String getDictLabel(String dictType, String dictValue, String separator);

    /**
     * 根据字典类型和字典值获取字典标签
     *
     * @param dictType 字典类型
     * @param dictLabel 标签
     * @param separator 分隔符
     * @return 字典标签
     */
    String getDictValue(String dictType, String dictLabel, String separator);
}
