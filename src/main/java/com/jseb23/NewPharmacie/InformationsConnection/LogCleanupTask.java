package com.jseb23.NewPharmacie.InformationsConnection;

import com.jseb23.NewPharmacie.Repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Component
public class LogCleanupTask {

    @Autowired
    private LogRepository logRepository;

    @Scheduled(cron = "0 0 0 * * ?") // Tous les jours à minuit
    public void cleanUpLogs()
    {
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);


        logRepository.deleteLogsOlderThan(oneWeekAgo);
    }
}
