package com.flybirds.swagger.annotation;

import com.flybirds.swagger.configuration.SwaggerAutoConfiguration;
import org.springframework.context.annotation.Import;
import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({ SwaggerAutoConfiguration.class })
public @interface EnableCustomSwagger2 {

}
