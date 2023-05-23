package com.greenbone.admin.util;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Util {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
