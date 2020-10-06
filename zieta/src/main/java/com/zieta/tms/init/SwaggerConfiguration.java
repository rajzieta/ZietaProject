package com.zieta.tms.init;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {                                    
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()
          .apis(RequestHandlerSelectors.basePackage( "com.zieta.tms" ))              
          .paths(PathSelectors.any())                          
          .build()
          .apiInfo(apiInfo());                                           
    }
    
    
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Timesheet Management System",
                "Provides TMS Rest endpoints services","1.0",
                "",
                "sales@zietatech.com",
                "","");
    }
}
