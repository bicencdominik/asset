package cz.bicenc.asset.configuration;


import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = {
        "cz.bicenc.asset.persistence.entity"
})
@EnableJpaRepositories(basePackages = {
        "cz.bicenc.asset.persistence.repository"
})
public class DataConfig implements RepositoryRestConfigurer {

}
