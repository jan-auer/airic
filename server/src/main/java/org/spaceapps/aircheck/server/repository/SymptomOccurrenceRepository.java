package org.spaceapps.aircheck.server.repository;

import org.spaceapps.aircheck.server.domain.SymptomOccurrence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SymptomOccurrenceRepository extends JpaRepository<SymptomOccurrence, Long> {

}
