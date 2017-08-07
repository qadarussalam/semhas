package io.github.semhas.service;

import io.github.semhas.domain.enumeration.StatusSeminar;
import io.github.semhas.service.dto.PesertaSeminarDTO;
import io.github.semhas.service.dto.SeminarDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * Service Interface for managing Seminar.
 */
public interface SeminarService {

    /**
     * Save a seminar.
     *
     * @param seminarDTO the entity to save
     * @return the persisted entity
     */
    SeminarDTO save(SeminarDTO seminarDTO);

    /**
     *  Get all the seminars.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SeminarDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" seminar.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SeminarDTO findOne(Long id);

    /**
     *  Delete the "id" seminar.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    Page<SeminarDTO> searchByJudul(String query, Pageable pageable);

    Page<SeminarDTO> findAllByStatus(StatusSeminar status, Pageable pageable);

    Page<SeminarDTO> findAllByStatusAndDosenId(StatusSeminar status, Long dosenId, Pageable pageable);

    Page<SeminarDTO> findAllByDosenId(Long dosenId, Pageable pageable);

    List<PesertaSeminarDTO> findPesertaSeminar(Long id);

    @Scheduled(cron = "0 14 21 * * ?")
    void sendSeminarReminderEmailNotification();

    Page<SeminarDTO> searchByJudulAndUserNotRegistered(String query, Long idMahasiswa, Pageable pageable);

    Page<SeminarDTO> searchByJudulAndStatus(String query, StatusSeminar status, Pageable pageable);

    Page<SeminarDTO> searchByJudulAndUserNotRegisteredAndStatus(String query, Long idMahasiswa, StatusSeminar status, Pageable pageable);
}
