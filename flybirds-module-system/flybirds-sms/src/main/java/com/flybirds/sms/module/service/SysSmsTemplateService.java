package com.flybirds.sms.module.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flybirds.common.util.result.Result;
import com.flybirds.mybatis.core.condition.Params;
import com.flybirds.mybatis.core.page.PageResult;
import com.flybirds.sms.module.core.vo.template.SysSmsTemplateCreateReqVO;
import com.flybirds.sms.module.core.vo.template.SysSmsTemplateExportReqVO;
import com.flybirds.sms.module.core.vo.template.SysSmsTemplatePageReqVO;
import com.flybirds.sms.module.core.vo.template.SysSmsTemplateUpdateReqVO;
import com.flybirds.sms.module.entity.SysSmsTemplate;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 短信模板 Service 接口
 *
 * @author zzf
 * @date 2021/1/25 9:24
 */
public interface SysSmsTemplateService {

    /**
     * 初始化短信模板的本地缓存
     */
    void initLocalCache();

    /**
     * 获得短信模板
     *
     * @param code 模板编码
     * @return 短信模板
     */
    SysSmsTemplate getSmsTemplateByCode(String code);

    /**
     * 获得短信模板，从缓存中
     *
     * @param code 模板编码
     * @return 短信模板
     */
    SysSmsTemplate getSmsTemplateByCodeFromCache(String code);

    /**
     * 格式化短信内容
     *
     * @param content 短信模板的内容
     * @param params 内容的参数
     * @return 格式化后的内容
     */
    String formatSmsTemplateContent(String content, Map<String, Object> params);

    /**
     * 创建短信模板
     *
     * @param sysSmsTemplate
     * @return 编号
     */
    Result<?> saveSmsTemplate(@Valid SysSmsTemplate sysSmsTemplate);


    /**
     * 更新短信模板
     *
     * @param updateReqVO 更新信息
     */
    Result<?> updateSmsTemplate(@Valid SysSmsTemplateUpdateReqVO updateReqVO);

    /**
     * 删除短信模板
     *
     * @param id 编号
     */
    Result<?> deleteSmsTemplate(Long id);

    /**
     * 获得短信模板
     *
     * @param id 编号
     * @return 短信模板
     */
    SysSmsTemplate getSmsTemplate(Long id);

    /**
     * 获得短信模板列表
     *
     * @param ids 编号
     * @return 短信模板列表
     */
    List<SysSmsTemplate> getSmsTemplateList(Collection<Long> ids);

    /**
     * 获得短信模板分页
     *
     * @param params 分页查询
     * @return 短信模板分页
     */
   IPage<SysSmsTemplate> getSmsTemplatePage(Params params);

    /**
     * 获得短信模板列表, 用于 Excel 导出
     *
     * @param params 查询条件
     * @return 短信模板分页
     */
    List<SysSmsTemplate> getSmsTemplateList(Params params);

    /**
     * 获得指定短信渠道下的短信模板数量
     *
     * @param channelId 短信渠道编号
     * @return 数量
     */
    Integer countByChannelId(Long channelId);

    /**
     * 模板导出
     * @param response
     * @param params
     * @return
     */
    Result exportSmsTemplateExcel(HttpServletResponse response, @Valid Params params);
    /**
     * 创建短信模板
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSmsTemplate(@Valid SysSmsTemplateCreateReqVO createReqVO);
    /**
     * 获得短信模板分页
     *
     * @param pageVO 分页查询
     * @return 短信模板分页
     */
    PageResult<SysSmsTemplate> getSmsTemplatePage(@Valid SysSmsTemplatePageReqVO pageVO);

    /**
     * 获得短信模板列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 短信模板分页
     */
    List<SysSmsTemplate> getSmsTemplateList(@Valid SysSmsTemplateExportReqVO exportReqVO);
}
