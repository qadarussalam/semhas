package io.github.semhas.service;

import io.github.semhas.service.dto.PesertaSeminarDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing PesertaSeminar.
 */
public interface PesertaSeminarService {

    /**
     * Save a pesertaSeminar.
     *
     * @param pesertaSeminarDTO the entity to save
     * @return the persisted entity
     */
    PesertaSeminarDTO save(PesertaSeminarDTO pesertaSeminarDTO);

    /**
     *  Get all the pesertaSeminars.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PesertaSeminarDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" pesertaSeminar.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PesertaSeminarDTO findOne(Long id);

    /**
     *  Delete the "id" pesertaSeminar.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
