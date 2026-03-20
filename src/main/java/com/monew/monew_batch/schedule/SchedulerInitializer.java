package com.monew.monew_batch.schedule;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchedulerInitializer {

    private final UserCleanupScheduler userCleanupScheduler;
    private final ArticleJobScheduler articleJobScheduler;

    @EventListener(ApplicationReadyEvent.class)
    public void runSchedulersOnStartup() {
        log.info("=== 서버 시작 시 스케줄러 강제 실행 ===");
        userCleanupScheduler.cleanupDeletedUsers();
        articleJobScheduler.runNotificationDeleteJob();
        articleJobScheduler.runArticleToS3Job();
        articleJobScheduler.runRssArticleCollectionJob();
        articleJobScheduler.runApiArticleCollectionJob();
    }
}
