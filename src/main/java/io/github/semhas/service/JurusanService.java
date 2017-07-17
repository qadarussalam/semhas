package io.github.semhas.service;

import io.github.semhas.service.dto.JurusanDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Jurusan.
 */
public interface JurusanService {

    /**
     * Save a jurusan.
     *
     * @param jurusanDTO the entity to save
     * @return the persisted entity
     */
    JurusanDTO save(JurusanDTO jurusanDTO);

    /**
     *  Get all the jurusans.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<JurusanDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" jurusan.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    JurusanDTO findOne(Long id);

    /**
     *  Delete the "id" jurusan.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
