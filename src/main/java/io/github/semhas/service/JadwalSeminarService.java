package io.github.semhas.service;

import io.github.semhas.service.dto.JadwalSeminarDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing JadwalSeminar.
 */
public interface JadwalSeminarService {

    /**
     * Save a jadwalSeminar.
     *
     * @param jadwalSeminarDTO the entity to save
     * @return the persisted entity
     */
    JadwalSeminarDTO save(JadwalSeminarDTO jadwalSeminarDTO);

    /**
     *  Get all the jadwalSeminars.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<JadwalSeminarDTO> findAll(Pageable pageable);
    /**
     *  Get all the JadwalSeminarDTO where Seminar is null.
     *
     *  @return the list of entities
     */
    List<JadwalSeminarDTO> findAllWhereSeminarIsNull();

    /**
     *  Get the "id" jadwalSeminar.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    JadwalSeminarDTO findOne(Long id);

    /**
     *  Delete the "id" jadwalSeminar.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
