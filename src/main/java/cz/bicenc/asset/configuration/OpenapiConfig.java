package cz.bicenc.asset.configuration;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenapiConfig {

    @Value("http://localhost:${server.port}")
    private String basePath;

    @Bean
    public OpenAPI config() {
        return new OpenAPI()
                .addServersItem(pusreLocalhostServer())
                .addServersItem(apiServer());
    }

    private Server pusreLocalhostServer() {
        return new Server()
                .description("Local server")
                .url(basePath);
    }
    private Server apiServer() {
        return new Server()
                .description("Local API Gateway")
                .url("http://localhost:8080");
    }

}
