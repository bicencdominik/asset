package cz.bicenc.asset.configuration;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@ComponentScan(basePackages = {"cz.nam.oneboxportal.context"})
@EnableJpaAuditing(auditorAwareRef = "auditor")
public class AppConfig {

    @Bean
    public AuditorAware<String> auditor() {
        return new AuditorResolver();
    }


    @Bean("annotationValidator")
    public Validator getAnnotationValidator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }


}
