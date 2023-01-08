package truthordare.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import truthordare.service.SessionCacheService;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfiguration {

    @Bean
    public SessionCacheService cacheExclude() {
        return new SessionCacheService(24, TimeUnit.HOURS);
    }

}