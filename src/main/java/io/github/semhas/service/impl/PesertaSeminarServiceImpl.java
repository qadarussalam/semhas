package io.github.semhas.service.impl;

import io.github.semhas.service.PesertaSeminarService;
import io.github.semhas.domain.PesertaSeminar;
import io.github.semhas.repository.PesertaSeminarRepository;
import io.github.semhas.service.dto.PesertaSeminarDTO;
import io.github.semhas.service.mapper.PesertaSeminarMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing PesertaSeminar.
 */
@Service
@Transactional
public class PesertaSeminarServiceImpl implements PesertaSeminarService{

    private final Logger log = LoggerFactory.getLogger(PesertaSeminarServiceImpl.class);

    private final PesertaSeminarRepository pesertaSeminarRepository;

    private final PesertaSeminarMapper pesertaSeminarMapper;

    public PesertaSeminarServiceImpl(PesertaSeminarRepository pesertaSeminarRepository, PesertaSeminarMapper pesertaSeminarMapper) {
        this.pesertaSeminarRepository = pesertaSeminarRepository;
        this.pesertaSeminarMapper = pesertaSeminarMapper;
    }

    /**
     * Save a pesertaSeminar.
     *
     * @param pesertaSeminarDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PesertaSeminarDTO save(PesertaSeminarDTO pesertaSeminarDTO) {
        log.debug("Request to save PesertaSeminar : {}", pesertaSeminarDTO);
        PesertaSeminar pesertaSeminar = pesertaSeminarMapper.toEntity(pesertaSeminarDTO);
        pesertaSeminar = pesertaSeminarRepository.save(pesertaSeminar);
        return pesertaSeminarMapper.toDto(pesertaSeminar);
    }

    /**
     *  Get all the pesertaSeminars.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PesertaSeminarDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PesertaSeminars");
        return pesertaSeminarRepository.findAll(pageable)
            .map(pesertaSeminarMapper::toDto);
    }

    /**
     *  Get one pesertaSeminar by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PesertaSeminarDTO findOne(Long id) {
        log.debug("Request to get PesertaSeminar : {}", id);
        PesertaSeminar pesertaSeminar = pesertaSeminarRepository.findOne(id);
        return pesertaSeminarMapper.toDto(pesertaSeminar);
    }

    /**
     *  Delete the  pesertaSeminar by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PesertaSeminar : {}", id);
        pesertaSeminarRepository.delete(id);
    }
}
