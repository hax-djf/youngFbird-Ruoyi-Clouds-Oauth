package com.flybirds.oauth.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.flybirds.common.util.result.AjaxResult;
import com.flybirds.common.util.str.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * 自定义异常返回使用jakon序列化
 *
 *
 * @author: ruoyi
 **/
@Component
public class CustomOauthExceptionSerializer extends StdSerializer<CustomOauthException> {

    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(CustomOauthExceptionSerializer.class);

    public static final String BAD_CREDENTIALS = "Bad credentials"; //无意义的客户端数据

    public CustomOauthExceptionSerializer()
    {
        super(CustomOauthException.class);
    }

    @Override
    public void serialize(CustomOauthException e, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {

        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField(AjaxResult.CODE_TAG, e.getHttpErrorCode());

        if (StringUtils.equals(e.getMessage(), BAD_CREDENTIALS)) {
            jsonGenerator.writeStringField(AjaxResult.MSG_TAG, "无意义的客户端数据（账号或者密码错误）");

        } else {
            jsonGenerator.writeStringField(AjaxResult.MSG_TAG, e.getMessage());
        }
        log.warn("oauth2 认证异常 {} ", e.getMessage());
        if (e.getAdditionalInformation()!=null) {
            for (Map.Entry<String, String> entry : e.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                jsonGenerator.writeStringField(key, add);
            }
        }
        jsonGenerator.writeEndObject();
    }
}