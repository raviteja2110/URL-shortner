package https.github.com.raviteja2110.URL.shortner.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebConfig {

    @Bean
    public RestTemplate getRetTemplate(){
        return new RestTemplate();
    }
}
