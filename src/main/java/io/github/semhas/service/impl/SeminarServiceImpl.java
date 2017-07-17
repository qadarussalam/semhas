package io.github.semhas.service.impl;

import io.github.semhas.service.SeminarService;
import io.github.semhas.domain.Seminar;
import io.github.semhas.repository.SeminarRepository;
import io.github.semhas.service.dto.SeminarDTO;
import io.github.semhas.service.mapper.SeminarMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Seminar.
 */
@Service
@Transactional
public class SeminarServiceImpl implements SeminarService{

    private final Logger log = LoggerFactory.getLogger(SeminarServiceImpl.class);

    private final SeminarRepository seminarRepository;

    private final SeminarMapper seminarMapper;

    public SeminarServiceImpl(SeminarRepository seminarRepository, SeminarMapper seminarMapper) {
        this.seminarRepository = seminarRepository;
        this.seminarMapper = seminarMapper;
    }

    /**
     * Save a seminar.
     *
     * @param seminarDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SeminarDTO save(SeminarDTO seminarDTO) {
        log.debug("Request to save Seminar : {}", seminarDTO);
        Seminar seminar = seminarMapper.toEntity(seminarDTO);
        seminar = seminarRepository.save(seminar);
        return seminarMapper.toDto(seminar);
    }

    /**
     *  Get all the seminars.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SeminarDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Seminars");
        return seminarRepository.findAll(pageable)
            .map(seminarMapper::toDto);
    }

    /**
     *  Get one seminar by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SeminarDTO findOne(Long id) {
        log.debug("Request to get Seminar : {}", id);
        Seminar seminar = seminarRepository.findOne(id);
        return seminarMapper.toDto(seminar);
    }

    /**
     *  Delete the  seminar by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Seminar : {}", id);
        seminarRepository.delete(id);
    }
}
