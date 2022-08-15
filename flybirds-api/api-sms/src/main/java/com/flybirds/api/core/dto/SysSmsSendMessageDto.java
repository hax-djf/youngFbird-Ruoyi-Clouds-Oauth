package com.flybirds.api.core.dto;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.flybirds.api.core.enums.SmsSendMessageTypeEnum;
import com.flybirds.common.exception.BaseException;
import com.flybirds.common.util.core.KeyValue;
import com.flybirds.common.util.math.RandomValueUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * 短信发送消息
 *
 * @author 芋道源码
 */
@Data
@Accessors(chain = true)
public class SysSmsSendMessageDto {

    /**
     * 短信日志编号
     */
    @NotNull(message = "短信日志编号不能为空")
    private Long logId;
    /**
     * 手机号
     */
    @NotNull(message = "手机号不能为空")
    private String mobile;
    /**
     * 短信渠道编号
     */
    @NotNull(message = "短信渠道编号不能为空")
    private Long channelId;
    /**
     * 短信 API 的模板编号
     */
    @NotNull(message = "短信 API 的模板编号不能为空")
    private String apiTemplateId;
    /**
     * 短信模板参数 -> key value 根据自己短信模板中${code}为准
     */
    private List<KeyValue<String, Object>> templateParams;

    /**
     * 短信缓存key
     */
    private List<KeyValue<String, String>> smsCacheKeyParams;

    public SysSmsSendMessageDto buildSmsMessage(String mobile, SmsSendMessageTypeEnum typeEnum){
        /**
         * {@link IdWorker#getId()}
         */
        this.logId = IdWorker.getId();
        this.mobile = mobile;
        List<KeyValue<String, Object>> valueList1 = new ArrayList<>(1);
        List<KeyValue<String, String>> valueList2 = new ArrayList<>(1);
        boolean flag = false;
        this.channelId = typeEnum.getChannelId();
        this.apiTemplateId = typeEnum.getApiTemplateId();
        String mobileCode = RandomValueUtil.getMobileCode();
        switch (typeEnum){
            case LOGIN_SYSTEM_MOBILE:
                flag = true;
                KeyValue<String, Object> keyValue = new KeyValue<>("code",mobileCode);
                valueList1.add(keyValue);
                this.templateParams = valueList1;
                Supplier<List<KeyValue<String,String>>> supplier = ()->{
                    KeyValue<String,String> map = new KeyValue<>();
                    map.setKey(typeEnum.getSmsKeyPrefix() + mobile);
                    map.setValue(mobileCode);
                    valueList2.add(map);
                   return  valueList2;
                };
                this.smsCacheKeyParams = ObjectUtil.isNotNull(typeEnum.getSmsKeyPrefix()) ? supplier.get() : null;
                break;
            case REGISTER_SYSTEM_MOBILE:
                flag = true;
                KeyValue<String, Object> keyValue_register = new KeyValue<>("code",mobileCode);
                valueList1.add(keyValue_register);
                this.templateParams = valueList1;
                Supplier<List<KeyValue<String,String>>> supplier_register = ()->{
                    KeyValue<String,String> map = new KeyValue<>();
                    map.setKey(typeEnum.getSmsKeyPrefix() + mobile);
                    map.setValue(mobileCode);
                    valueList2.add(map);
                    return  valueList2;
                };
                this.smsCacheKeyParams = ObjectUtil.isNotNull(typeEnum.getSmsKeyPrefix()) ? supplier_register.get() : null;
                break;

            case RETRIEVE_PWD_SYSTEM_CODE:
                flag = true;
                KeyValue<String, Object> keyValue_back = new KeyValue<>("code",mobileCode);
                valueList1.add(keyValue_back);
                this.templateParams = valueList1;
                Supplier<List<KeyValue<String,String>>> supplier_back = ()->{
                    KeyValue<String,String> map = new KeyValue<>();
                    map.setKey(typeEnum.getSmsKeyPrefix() + mobile);
                    map.setValue(mobileCode);
                    valueList2.add(map);
                    return  valueList2;
                };
                this.smsCacheKeyParams = ObjectUtil.isNotNull(typeEnum.getSmsKeyPrefix()) ? supplier_back.get() : null;
                break;
            case UPDATE_PHONE_NUMBER_CODE:
                flag = true;
                KeyValue<String, Object> keyValue_update = new KeyValue<>("code",mobileCode);
                valueList1.add(keyValue_update);
                this.templateParams = valueList1;
                Supplier<List<KeyValue<String,String>>> supplier_update_phone = ()->{
                    KeyValue<String,String> map = new KeyValue<>();
                    map.setKey(typeEnum.getSmsKeyPrefix() + mobile);
                    map.setValue(mobileCode);
                    valueList2.add(map);
                    return  valueList2;
                };
                this.smsCacheKeyParams = ObjectUtil.isNotNull(typeEnum.getSmsKeyPrefix()) ? supplier_update_phone.get() : null;
                break;
        }
        if(!flag){
            throw new BaseException("找不到对应的短信发送类型");
        }
        return this;
    }
}
