package io.github.semhas.repository;

import io.github.semhas.domain.Jurusan;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Jurusan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JurusanRepository extends JpaRepository<Jurusan,Long> {
    
}
