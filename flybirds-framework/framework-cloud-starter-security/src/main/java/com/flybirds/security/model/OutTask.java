package com.flybirds.security.model;

import com.flybirds.common.enums.login.AccountLoginOutChannelEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户退任务模型
 *
 * @author :flybirds
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OutTask implements Serializable{

    private String tokenValue;
    /**
     * 退出状态 {@link AccountLoginOutChannelEnum#ADMIN_EXIT}
     */
    private AccountLoginOutChannelEnum OutChannel;

}
