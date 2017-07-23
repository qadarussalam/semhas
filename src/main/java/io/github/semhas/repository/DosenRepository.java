package io.github.semhas.repository;

import io.github.semhas.domain.Dosen;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Dosen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DosenRepository extends JpaRepository<Dosen,Long> {

    Dosen findOneByUserLogin(String username);
}
