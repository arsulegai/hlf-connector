package hlf.java.rest.client.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import java.util.List;

@Data
@Configuration
@PropertySource(
        value = {"${server.config.location}"},
        ignoreResourceNotFound = true)
@RefreshScope
public class ServerProperties {

    @Value("${server.config.location}")
    private String configLocation;
}
