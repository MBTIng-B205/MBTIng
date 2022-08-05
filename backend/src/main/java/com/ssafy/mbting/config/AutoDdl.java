package com.ssafy.mbting.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoDdl {
    @Bean
    AutoDdlStrategy autoDdlStrategy(@Value("${com.mbting.ddl_auto}") String autoDdlStrategy) {
        if (autoDdlStrategy == null)
            return null;
        if (autoDdlStrategy.equalsIgnoreCase("CREATE"))
            return AutoDdlStrategy.CREATE;
        if (autoDdlStrategy.equalsIgnoreCase("UPDATE"))
            return AutoDdlStrategy.UPDATE;
        return null;
    }
}
