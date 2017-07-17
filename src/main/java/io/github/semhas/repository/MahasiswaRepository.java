package io.github.semhas.repository;

import io.github.semhas.domain.Mahasiswa;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Mahasiswa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MahasiswaRepository extends JpaRepository<Mahasiswa,Long> {
    
}
