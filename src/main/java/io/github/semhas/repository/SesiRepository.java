package io.github.semhas.repository;

import io.github.semhas.domain.Sesi;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Sesi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SesiRepository extends JpaRepository<Sesi,Long> {
    
}
