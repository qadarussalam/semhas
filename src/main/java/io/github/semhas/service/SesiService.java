package io.github.semhas.service;

import io.github.semhas.service.dto.SesiDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Sesi.
 */
public interface SesiService {

    /**
     * Save a sesi.
     *
     * @param sesiDTO the entity to save
     * @return the persisted entity
     */
    SesiDTO save(SesiDTO sesiDTO);

    /**
     *  Get all the sesis.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SesiDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" sesi.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SesiDTO findOne(Long id);

    /**
     *  Delete the "id" sesi.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
