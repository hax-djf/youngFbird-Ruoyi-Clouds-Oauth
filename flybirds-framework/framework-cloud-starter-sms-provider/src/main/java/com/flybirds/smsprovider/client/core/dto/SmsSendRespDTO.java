package com.flybirds.smsprovider.client.core.dto;

import lombok.Data;

/**
 * 短信发送 Response DTO
 *
 * @author
 */
@Data
public class SmsSendRespDTO {

    /**
     * 短信 API 发送返回的序号
     */
    private String serialNo;

}
