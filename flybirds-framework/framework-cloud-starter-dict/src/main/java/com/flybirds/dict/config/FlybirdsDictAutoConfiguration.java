package com.flybirds.dict.config;

import com.flybirds.dict.core.service.DictDataFrameworkService;
import com.flybirds.dict.core.util.DictFrameworkUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlybirdsDictAutoConfiguration {

    @Bean
    @SuppressWarnings("InstantiationOfUtilityClass")
    public DictFrameworkUtils dictUtils(DictDataFrameworkService service) {
        DictFrameworkUtils.init(service);
        return new DictFrameworkUtils();
    }

}
