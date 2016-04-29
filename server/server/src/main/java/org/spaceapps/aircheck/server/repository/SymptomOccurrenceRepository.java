package org.spaceapps.aircheck.server.repository;

import org.spaceapps.aircheck.server.domain.SymptomOccurrence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface SymptomOccurrenceRepository extends JpaRepository<SymptomOccurrence, Long> {

    @Query("SELECT s FROM SymptomOccurrence s WHERE s.date > :start AND s.date > :end")
    List<SymptomOccurrence> findByTimespan(@Param("start") Date start, @Param("end") Date end);

}
