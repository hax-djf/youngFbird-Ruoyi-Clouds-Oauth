package com.flybirds.common.core.model;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author :flybirds
 * @create :2021-05-15 17:28:00
 * @description :
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginMobileBody extends LoginBody {

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
