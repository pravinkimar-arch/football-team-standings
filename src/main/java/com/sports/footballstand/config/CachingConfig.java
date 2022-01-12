package com.sports.footballstand.config;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import com.sports.footballstand.services.PopulateCache;
import com.sports.footballstand.utils.FootballStandingRestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableCaching
@EnableScheduling
//@Slf4j - isssue with lombok plugin in intellij version
public class CachingConfig {

    private static final Logger log = LoggerFactory.getLogger(CachingConfig.class);

    private static final String FOOTBALL = "Football";
    private static final int port = 443;
    private static final String ACTION = "action";
    private static final String LEAGUE_ACTION = "get_leagues";

    private final FootballStandingRestUtils footballStandingRestUtils;
    private final PopulateCache populateCache;

    @Value("${football.standing.base.url}")
    private String baseFootballUrl;

    @Autowired
    CachingConfig(FootballStandingRestUtils footballStandingRestUtils,
                  PopulateCache populateCache) {
        this.footballStandingRestUtils = footballStandingRestUtils;
        this.populateCache = populateCache;
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(FOOTBALL);
    }

    @Scheduled(fixedDelay = 300000, initialDelay = 2000)
    private void ReportCacheEvict() {
        if (footballStandingRestUtils.CheckHostAvailability()) {
            log.info("Host is available...");
            DateTimeFormatter datetimeFormater = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            cacheManager().getCache(FOOTBALL).clear();
            log.info("Performed cache flush at " + datetimeFormater.format(now));
            populateCache.populate();
        } else {
            log.info("Dependent host available, skipped cache flush");
        }
    }
}