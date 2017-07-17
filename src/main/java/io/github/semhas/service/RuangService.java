package io.github.semhas.service;

import io.github.semhas.service.dto.RuangDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Ruang.
 */
public interface RuangService {

    /**
     * Save a ruang.
     *
     * @param ruangDTO the entity to save
     * @return the persisted entity
     */
    RuangDTO save(RuangDTO ruangDTO);

    /**
     *  Get all the ruangs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RuangDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" ruang.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RuangDTO findOne(Long id);

    /**
     *  Delete the "id" ruang.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
