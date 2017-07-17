package io.github.semhas.service.impl;

import io.github.semhas.service.SesiService;
import io.github.semhas.domain.Sesi;
import io.github.semhas.repository.SesiRepository;
import io.github.semhas.service.dto.SesiDTO;
import io.github.semhas.service.mapper.SesiMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Sesi.
 */
@Service
@Transactional
public class SesiServiceImpl implements SesiService{

    private final Logger log = LoggerFactory.getLogger(SesiServiceImpl.class);

    private final SesiRepository sesiRepository;

    private final SesiMapper sesiMapper;

    public SesiServiceImpl(SesiRepository sesiRepository, SesiMapper sesiMapper) {
        this.sesiRepository = sesiRepository;
        this.sesiMapper = sesiMapper;
    }

    /**
     * Save a sesi.
     *
     * @param sesiDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SesiDTO save(SesiDTO sesiDTO) {
        log.debug("Request to save Sesi : {}", sesiDTO);
        Sesi sesi = sesiMapper.toEntity(sesiDTO);
        sesi = sesiRepository.save(sesi);
        return sesiMapper.toDto(sesi);
    }

    /**
     *  Get all the sesis.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SesiDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sesis");
        return sesiRepository.findAll(pageable)
            .map(sesiMapper::toDto);
    }

    /**
     *  Get one sesi by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SesiDTO findOne(Long id) {
        log.debug("Request to get Sesi : {}", id);
        Sesi sesi = sesiRepository.findOne(id);
        return sesiMapper.toDto(sesi);
    }

    /**
     *  Delete the  sesi by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sesi : {}", id);
        sesiRepository.delete(id);
    }
}
