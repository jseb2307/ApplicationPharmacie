package com.jseb23.NewPharmacie.Repository;

import com.jseb23.NewPharmacie.InformationsConnection.Log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface LogRepository extends JpaRepository<Log, Long>
{
    @Transactional
    @Modifying
    @Query("DELETE FROM Log l WHERE l.timestamp < :cutoffDate") // requête pour ne garder que la dernière semaine

    void deleteLogsOlderThan(@Param("cutoffDate") LocalDateTime cutoffDate);
}
