package com.flybirds.common.enums.login;

/**
 * oauth2.0登录模式
 *
 * @author :flybirds
 */
public enum AccountLoginGrantType {


    OAUTH2_PASSWORD("password", "密码"),
    OAUTH2_SOCIAL("social", "社交"),
    OAUTH2_CODE("authorization_code", "授权码"),
    OAUTH2_CLIENT_CREDENTIAL("client_credential","客户端"),
    OAUTH2_TOKEN("implicit","简化端"),
    OAUTH2_SMS("sms","短信验证码");

    private String value;

    private String label;

    AccountLoginGrantType(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public static final String OAUTH2_PASSWORD_CONSTANT = "password";
    public static final String OAUTH2_CODE_CONSTANT = "authorization_code";
    public static final String OAUTH2_CLIENT_CREDENTIAL_CONSTANT = "client_credential";
    public static final String OAUTH2_SMS_CONSTANT = "sms";
    public static final String OAUTH2_TOKEN_CONSTANT = "implicit";
    public static final String OAUTH2_TYPE_CONSTANT = "grant_type";
    public static final String OAUTH2_SOCIAL_CONSTANT = "social";


}
