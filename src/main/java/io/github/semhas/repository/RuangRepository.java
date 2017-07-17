package io.github.semhas.repository;

import io.github.semhas.domain.Ruang;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Ruang entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RuangRepository extends JpaRepository<Ruang,Long> {
    
}
