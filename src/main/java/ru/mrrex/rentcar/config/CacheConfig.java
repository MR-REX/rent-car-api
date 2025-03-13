package ru.mrrex.rentcar.config;

import java.time.Duration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofMinutes(5))
            .maximumSize(128);
    }

    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeineConfig) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager(
            "brands", "categories", "cars", "carsByBrand", "carsByCategory"
        );

        caffeineCacheManager.setCaffeine(caffeineConfig);

        return caffeineCacheManager;
    }
}
