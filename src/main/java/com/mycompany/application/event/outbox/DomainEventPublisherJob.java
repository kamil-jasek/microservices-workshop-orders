package com.mycompany.application.event.outbox;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;

import static java.util.concurrent.TimeUnit.SECONDS;
import static net.javacrumbs.shedlock.core.LockAssert.assertLocked;

@RequiredArgsConstructor
class DomainEventPublisherJob {

    private final @NonNull KafkaOutboxDomainEventPublisher publisher;

    @Scheduled(fixedRate = 5, timeUnit = SECONDS)
    @SchedulerLock(name = "domain_event_publisher_job", lockAtLeastFor = "5s", lockAtMostFor = "5s")
    void execute() {
        assertLocked();
        publisher.publish();
    }
}