package org.spaceapps.aircheck.server.repository;

import org.spaceapps.aircheck.server.domain.Sample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SampleRepository extends JpaRepository<Sample, Long> {

    @Query("SELECT s FROM Sample s WHERE s.date > :start AND s.date > :end")
    List<Sample> findByTimespan(@Param("start") Date start, @Param("end") Date end);

}
