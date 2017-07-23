package io.github.semhas.repository;

import io.github.semhas.domain.PesertaSeminar;
import io.github.semhas.domain.Seminar;
import io.github.semhas.domain.enumeration.AbsensiSeminar;
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
    @Query("select distinct ps.seminar from PesertaSeminar ps where ps.mahasiswa.id = ?1 and ps.absensi = ?2")
    List<Seminar> findDistinctSeminarByMahasiswaIdAndAbsensi(Long id, AbsensiSeminar hadir);
}
