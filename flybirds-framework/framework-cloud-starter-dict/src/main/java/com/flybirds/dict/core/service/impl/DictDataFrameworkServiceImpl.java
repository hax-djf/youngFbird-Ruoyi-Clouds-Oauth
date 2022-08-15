package com.flybirds.dict.core.service.impl;

import com.flybirds.common.constant.CacheConstantEnum;
import com.flybirds.common.util.spring.SpringUtils;
import com.flybirds.common.util.str.StringUtils;
import com.flybirds.dict.core.dto.DictDataCacheDTO;
import com.flybirds.dict.core.service.DictDataFrameworkService;
import com.flybirds.redis.manger.RedisCacheManger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DictDataFrameworkServiceImpl implements DictDataFrameworkService{

    /**
     * 获得指定的字典数据，从缓存中
     *
     * @param type 字典类型
     * @param value 字典数据值
     * @return 字典数据
     */
    @Override
    public DictDataCacheDTO parseDictDataFromCacheByValue(String type, String value){

        DictDataCacheDTO dicResult = null ;
        List<DictDataCacheDTO> dictCache = getDictCache(getCacheKey(type));

        if(StringUtils.isNotEmpty(dictCache)){
            List<DictDataCacheDTO> labelList = dictCache.stream().filter(dictDataCacheDTO -> Objects.equals(dictDataCacheDTO.getDictValue(), value)).collect(Collectors.toList());
            dicResult = labelList.get(0);
        }else{
            log.error("通过{} 解析类型未{} 字典数据异常",value,type);
        }
        return dicResult;
    }

    /**
     * 解析获得指定的字典数据，从缓存中
     *
     * @param type 字典类型
     * @param label 字典数据标签
     * @return 字典数据
     */
    @Override
    public DictDataCacheDTO parseDictDataFromCacheByLabel(String type, String label){

        DictDataCacheDTO dicResult = null ;
        List<DictDataCacheDTO> dictCache = getDictCache(getCacheKey(type));

        if(StringUtils.isNotEmpty(dictCache)){
            List<DictDataCacheDTO> labelList = dictCache.stream().filter(dictDataCacheDTO -> Objects.equals(dictDataCacheDTO.getDictLabel(), label)).collect(Collectors.toList());
            dicResult = labelList.get(0);
        }else{
            log.error("通过{} 解析类型未{} 字典数据异常",label,type);
        }
        return dicResult;
    }

    /**
     * 获得指定类型的字典数据，从缓存中
     *
     * @param type 字典类型
     * @return 字典数据列表
     */
    @Override
    public List<DictDataCacheDTO> listDictDatasFromCache(String type){

       return getDictCache(getCacheKey(type));
    }

    @Override
    public String getDictLabel(String dictType, String dictValue, String separator) {

        StringBuilder propertyString = new StringBuilder();
        List<DictDataCacheDTO> datas = getDictCache(dictType);

        if (StringUtils.containsAny(separator, dictValue) && StringUtils.isNotEmpty(datas))
        {
            for (DictDataCacheDTO dict : datas)
            {
                for (String value : dictValue.split(separator))
                {
                    if (value.equals(dict.getDictValue()))
                    {
                        propertyString.append(dict.getDictLabel() + separator);
                        break;
                    }
                }
            }
        }
        else
        {
            for (DictDataCacheDTO dict : datas)
            {
                if (dictValue.equals(dict.getDictValue()))
                {
                    return dict.getDictLabel();
                }
            }
        }
        return StringUtils.stripEnd(propertyString.toString(), separator);

    }

    @Override
    public String getDictValue(String dictType, String dictLabel, String separator) {


        StringBuilder propertyString = new StringBuilder();
        List<DictDataCacheDTO> datas = getDictCache(dictType);

        if (StringUtils.containsAny(separator, dictLabel) && StringUtils.isNotEmpty(datas))
        {
            for (DictDataCacheDTO dict : datas)
            {
                for (String label : dictLabel.split(separator))
                {
                    if (label.equals(dict.getDictLabel()))
                    {
                        propertyString.append(dict.getDictValue() + separator);
                        break;
                    }
                }
            }
        }
        else
        {
            for (DictDataCacheDTO dict : datas)
            {
                if (dictLabel.equals(dict.getDictLabel()))
                {
                    return dict.getDictValue();
                }
            }
        }
        return StringUtils.stripEnd(propertyString.toString(), separator);
    }

    /**
     * 获取字典缓存
     *
     * @param key 参数键
     * @return dictDatas 字典数据列表
     */
    public static List<DictDataCacheDTO> getDictCache(String key)
    {
        Object cacheObj = SpringUtils.getBean(RedisCacheManger.class).getCacheObject(getCacheKey(key));
        if (StringUtils.isNotNull(cacheObj))
        {
            List<DictDataCacheDTO> dictDatas = StringUtils.cast(cacheObj);
            return dictDatas;
        }
        return null;
    }

    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    public static String getCacheKey(String configKey)
    {
        return CacheConstantEnum.SYS_DICT_KEY.getKey() + configKey;
    }

}
