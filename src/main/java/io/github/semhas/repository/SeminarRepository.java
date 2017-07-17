package io.github.semhas.repository;

import io.github.semhas.domain.Seminar;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Seminar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeminarRepository extends JpaRepository<Seminar,Long> {
    
}
