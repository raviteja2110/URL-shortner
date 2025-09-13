package https.github.com.raviteja2110.url.shortner.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class AppProperties {
    /** The base URL of the application, picked up from APP_BASE_URL environment variable in production. */
    private String baseUrl;
}