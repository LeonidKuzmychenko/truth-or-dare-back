package truthordare.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import truthordare.dto.GameDto;

import java.util.concurrent.TimeUnit;

public class SessionCacheService {
    private final Cache<String, GameDto> cache;

    public SessionCacheService(int expiryDuration, TimeUnit timeUnit) {
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(expiryDuration, timeUnit)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .build();
    }

    public GameDto getGame(String key) {
        return cache.getIfPresent(key);
    }

    public void setGame(String key, GameDto gameDto) {
        if (key != null && gameDto != null) {
            cache.put(key, gameDto);
        }
    }

}