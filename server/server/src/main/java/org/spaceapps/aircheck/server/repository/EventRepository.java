package org.spaceapps.aircheck.server.repository;

import org.spaceapps.aircheck.server.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

}
