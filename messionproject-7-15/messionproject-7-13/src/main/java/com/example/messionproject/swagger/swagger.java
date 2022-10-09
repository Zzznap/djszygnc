package com.example.messionproject.swagger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

public class swagger {


    @Configuration
    @EnableSwagger2
    public class swaggerconfig {
        @Bean
        public Docket docket(){
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiinfo())
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.example.messionproject.controll"))
                    .paths(PathSelectors.any())
                    .build();
        }
        private ApiInfo apiinfo() {
            return new ApiInfoBuilder()
                    .title("系统服务API")
                    .description("平台服务接口文档说明")
                    .contact(new Contact("colin", "", "taotao@itsource.cn"))
                    .version("1.0")
                    .build();
        }
    }

}
