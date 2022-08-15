package com.flybirds.common.core.model;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author :flybirds
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SmsLoginReqVo0 extends LoginReqVo0 {

    private static final long serialVersionUID = 1574060026206L;
    /*
     * 用户名
     */
    private String mobile;

    /**
     * 验证码
     */
    private String mobileCode;

    /**
     * 用户名
     */
    private String userName;
}
