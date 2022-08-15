package com.flybirds.system.sysDicData.service.Impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flybirds.api.core.entity.SysDictData;
import com.flybirds.system.sysDicData.mapper.SysDictDataMapper;
import com.flybirds.system.sysDicData.mq.producer.SysDictDataProducer;
import com.flybirds.system.sysDicData.service.ISysDictDataService;
import com.flybirds.system.utils.DictUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 字典 业务层处理
 * 
 * @author ruoyi
 */
@Service
@Slf4j
@SuppressWarnings("all")
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper,SysDictData> implements ISysDictDataService{

    /**
     * 排序 dictType > sort
     */
    private static final Comparator<SysDictData> COMPARATOR_TYPE_AND_SORT = Comparator
            .comparing(SysDictData::getDictType)
            .thenComparingLong(SysDictData::getDictSort);

    private volatile Date maxUpdateTime;

    @Autowired
    private SysDictDataMapper dictDataMapper;

    @Resource
    private SysDictDataProducer dictDataProducer;


    //初始化操作
    public synchronized void initRedisCache() {
        // 获取字典数据列表，如果有更新
        List<SysDictData> dataList = this.loadDictDataIfUpdate(maxUpdateTime);

        if (CollUtil.isEmpty(dataList)) {
            return;
        }
        assert dataList.size() > 0; // 断言，避免告警
        maxUpdateTime = dataList.stream().max(Comparator.comparing(data -> {
            return Optional.ofNullable(data.getUpdateTime())
                    .orElseGet(data::getCreateTime);
        })).get().getUpdateTime();

        //分组缓存
        Map<String, List<SysDictData>> collectGroup = dataList.stream().collect(Collectors.groupingBy(SysDictData::getDictType));

        Iterator<Map.Entry<String, List<SysDictData>>> iterator = collectGroup.entrySet().iterator();

        if(iterator.hasNext()){
            Map.Entry<String, List<SysDictData>> entry = iterator.next();
            DictUtils.setDictCache(entry.getKey(),entry.getValue());
        }
        log.info("[initLocalCache][缓存字典数据，数量为:{}]", dataList.size());
    }


    /**
     * 如果字典数据发生变化，从数据库中获取最新的全量字典数据。
     * 如果未发生变化，则返回空
     *
     * @param maxUpdateTime 当前字典数据的最大更新时间
     * @return 字典数据列表
     */
    private List<SysDictData> loadDictDataIfUpdate(Date maxUpdateTime) {
        // 第一步，判断是否要更新。
        if (maxUpdateTime == null) { // 如果更新时间为空，说明 DB 一定有新数据
            log.info("[loadDictDataIfUpdate][首次加载全量字典数据]");
        } else { // 判断数据库中是否有更新的字典数据
            if (!dictDataMapper.selectExistsByUpdateTimeAfter(maxUpdateTime)) {
                return null;
            }
            //更具最新时间加载字典数据 = 增量发版
            log.info("[loadDictDataIfUpdate][增量加载全量字典数据]");
            return dictDataMapper.selectByUpdateTimeAfter(maxUpdateTime);
        }
        // 第二步，如果有更新，则从数据库加载所有字典数据
        return dictDataMapper.selectList();
    }

    /**
     * 根据条件分页查询字典数据
     * 
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictData> selectDictDataList(SysDictData dictData) {

        return dictDataMapper.selectDictDataList(dictData);
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     * 
     * @param dictType 字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    @Override
    public String selectDictLabel(String dictType, String dictValue) {

        return dictDataMapper.selectDictLabel(dictType, dictValue);
    }

    /**
     * 根据字典数据ID查询信息
     * 
     * @param dictCode 字典数据ID
     * @return 字典数据
     */
    @Override
    public SysDictData selectDictDataById(Long dictCode)
    {
        return dictDataMapper.selectDictDataById(dictCode);
    }

    /**
     * 批量删除字典数据信息
     * 
     * @param dictCodes 需要删除的字典数据ID
     * @return 结果
     */
    @Override
    public int deleteDictDataByIds(Long[] dictCodes) {

        int row = dictDataMapper.deleteDictDataByIds(dictCodes);
        if (row > 0) {
            //发送刷新消息
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    dictDataProducer.sendDictDataRefreshMessage();                }
            });
        }
        return row;
    }

    /**
     * 新增保存字典数据信息
     * 
     * @param dictData 字典数据信息
     * @return 结果
     */
    @Override
    public int insertDictData(SysDictData dictData) {
        int row = dictDataMapper.insertDictData(dictData);
        if (row > 0) {
            //发送刷新消息
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    dictDataProducer.sendDictDataRefreshMessage();                }
            });
        }
        return row;
    }

    /**
     * 修改保存字典数据信息
     * 
     * @param dictData 字典数据信息
     * @return 结果
     */
    @Override
    public int updateDictData(SysDictData dictData)
    {
        int row = dictDataMapper.updateDictData(dictData);
        if (row > 0) {
            //发送刷新消息
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    dictDataProducer.sendDictDataRefreshMessage();                }
            });
        }
        return row;
    }

    /**
     * 查询list数据
     * @return
     */
    @Override
    public List<SysDictData> getDictDatas() {
        List<SysDictData> list = dictDataMapper.selectList();
        list.sort(COMPARATOR_TYPE_AND_SORT);
        return list;
    }
}
