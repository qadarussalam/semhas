package io.github.semhas.service;

import io.github.semhas.service.dto.DosenDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Dosen.
 */
public interface DosenService {

    /**
     * Save a dosen.
     *
     * @param dosenDTO the entity to save
     * @return the persisted entity
     */
    DosenDTO save(DosenDTO dosenDTO);

    /**
     *  Get all the dosens.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DosenDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" dosen.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DosenDTO findOne(Long id);

    /**
     *  Delete the "id" dosen.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    DosenDTO findByUserLogin(String username);
}
