package io.github.semhas.service.impl;

import io.github.semhas.service.DosenService;
import io.github.semhas.domain.Dosen;
import io.github.semhas.repository.DosenRepository;
import io.github.semhas.service.dto.DosenDTO;
import io.github.semhas.service.mapper.DosenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Dosen.
 */
@Service
@Transactional
public class DosenServiceImpl implements DosenService{

    private final Logger log = LoggerFactory.getLogger(DosenServiceImpl.class);

    private final DosenRepository dosenRepository;

    private final DosenMapper dosenMapper;

    public DosenServiceImpl(DosenRepository dosenRepository, DosenMapper dosenMapper) {
        this.dosenRepository = dosenRepository;
        this.dosenMapper = dosenMapper;
    }

    /**
     * Save a dosen.
     *
     * @param dosenDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DosenDTO save(DosenDTO dosenDTO) {
        log.debug("Request to save Dosen : {}", dosenDTO);
        Dosen dosen = dosenMapper.toEntity(dosenDTO);
        dosen = dosenRepository.save(dosen);
        return dosenMapper.toDto(dosen);
    }

    /**
     *  Get all the dosens.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DosenDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Dosens");
        return dosenRepository.findAll(pageable)
            .map(dosenMapper::toDto);
    }

    /**
     *  Get one dosen by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DosenDTO findOne(Long id) {
        log.debug("Request to get Dosen : {}", id);
        Dosen dosen = dosenRepository.findOne(id);
        return dosenMapper.toDto(dosen);
    }

    /**
     *  Delete the  dosen by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dosen : {}", id);
        dosenRepository.delete(id);
    }

    @Override
    public DosenDTO findByUserLogin(String username) {
        Dosen d = dosenRepository.findOneByUserLogin(username);
        return dosenMapper.toDto(d);
    }
}
