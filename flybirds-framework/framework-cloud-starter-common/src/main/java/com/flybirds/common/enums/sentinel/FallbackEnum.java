package com.flybirds.common.enums.sentinel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * FallbackEnum异常枚举类
 *
 * @author :flybirds
 */
@AllArgsConstructor
@Getter
public enum FallbackEnum {

    FlowExceptionEnum(0,"网关异常处理请求超过最大数，请稍后再试"),

    DegradeExceptionEnum(0,"网关异常处理系统降级，请稍后再试"),

    ParamFlowExceptionEnum(0,"网关异常处理热点参数限流，请稍后再试"),

    SystemBlockExceptionEnum(0,"网关异常处理系统规则限流或降级，请稍后再试"),

    AuthorityExceptionEnum(0,"网关异常处理系统授权规则不通过，请稍后再试");

    private Integer value;

    private String label;
}
