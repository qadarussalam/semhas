package io.github.semhas.repository;

import io.github.semhas.domain.Mahasiswa;
import io.github.semhas.domain.Seminar;
import io.github.semhas.domain.enumeration.StatusSeminar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;


/**
 * Spring Data JPA repository for the Seminar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeminarRepository extends JpaRepository<Seminar,Long> {

    Page<Seminar> findAllByJudulContains(String query, Pageable pageable);

    Page<Seminar> findAllByStatus(StatusSeminar status, Pageable pageable);

    @Query("from Seminar s where s.status = ?1 and (s.dosenPertama.id = ?2 or s.dosenKedua.id = ?3)")
    Page<Seminar> findAllByStatusAndDosenPertamaIdOrDosenKeduaId(StatusSeminar status, Long dosenPertamaId, Long dosenKeduaId, Pageable pageable);

    Page<Seminar> findAllByDosenPertamaIdOrDosenKeduaId(Long dosenId, Long dosenId1, Pageable pageable);

    List<Seminar> findAllByJamMulaiBetween(ZonedDateTime from, ZonedDateTime to);

    @Query("select s from Seminar s where s.judul like ?1 and not exists(from PesertaSeminar ps where ps.seminar = s and ps.mahasiswa = ?2)")
    Page<Seminar> findAllByJudulContainsAndListPesertaSeminarsMahasiswaNot(String query, Mahasiswa mahasiswa, Pageable pageable);
}
