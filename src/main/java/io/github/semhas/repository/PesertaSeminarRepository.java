package io.github.semhas.repository;

import io.github.semhas.domain.PesertaSeminar;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the PesertaSeminar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PesertaSeminarRepository extends JpaRepository<PesertaSeminar,Long> {
}
