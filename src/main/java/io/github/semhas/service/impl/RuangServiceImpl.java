package io.github.semhas.service.impl;

import io.github.semhas.service.RuangService;
import io.github.semhas.domain.Ruang;
import io.github.semhas.repository.RuangRepository;
import io.github.semhas.service.dto.RuangDTO;
import io.github.semhas.service.mapper.RuangMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Ruang.
 */
@Service
@Transactional
public class RuangServiceImpl implements RuangService{

    private final Logger log = LoggerFactory.getLogger(RuangServiceImpl.class);

    private final RuangRepository ruangRepository;

    private final RuangMapper ruangMapper;

    public RuangServiceImpl(RuangRepository ruangRepository, RuangMapper ruangMapper) {
        this.ruangRepository = ruangRepository;
        this.ruangMapper = ruangMapper;
    }

    /**
     * Save a ruang.
     *
     * @param ruangDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RuangDTO save(RuangDTO ruangDTO) {
        log.debug("Request to save Ruang : {}", ruangDTO);
        Ruang ruang = ruangMapper.toEntity(ruangDTO);
        ruang = ruangRepository.save(ruang);
        return ruangMapper.toDto(ruang);
    }

    /**
     *  Get all the ruangs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RuangDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ruangs");
        return ruangRepository.findAll(pageable)
            .map(ruangMapper::toDto);
    }

    /**
     *  Get one ruang by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RuangDTO findOne(Long id) {
        log.debug("Request to get Ruang : {}", id);
        Ruang ruang = ruangRepository.findOne(id);
        return ruangMapper.toDto(ruang);
    }

    /**
     *  Delete the  ruang by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ruang : {}", id);
        ruangRepository.delete(id);
    }
}
