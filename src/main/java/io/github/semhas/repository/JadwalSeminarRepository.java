package io.github.semhas.repository;

import io.github.semhas.domain.JadwalSeminar;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the JadwalSeminar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JadwalSeminarRepository extends JpaRepository<JadwalSeminar,Long> {
    
}
