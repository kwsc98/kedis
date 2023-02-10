package pres.kedis.example.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author kwsc98
 */
@Component
@Getter
public class AppLicationConfig {

    @Value("${redisHost}")
    private String redisHost;

    @Value("${redisPort}")
    private int redisPort;

    @Value("${redisPassword}")
    private String redisPassword;

    @Value("${redisDatabase}")
    private int redisDatabase;


}
