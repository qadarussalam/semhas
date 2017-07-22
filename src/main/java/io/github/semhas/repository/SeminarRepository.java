package io.github.semhas.repository;

import io.github.semhas.domain.Seminar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Seminar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeminarRepository extends JpaRepository<Seminar,Long> {

    Page<Seminar> findAllByJudulContains(String query, Pageable pageable);
}
