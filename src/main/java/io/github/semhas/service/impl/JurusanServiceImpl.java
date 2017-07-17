package io.github.semhas.service.impl;

import io.github.semhas.service.JurusanService;
import io.github.semhas.domain.Jurusan;
import io.github.semhas.repository.JurusanRepository;
import io.github.semhas.service.dto.JurusanDTO;
import io.github.semhas.service.mapper.JurusanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Jurusan.
 */
@Service
@Transactional
public class JurusanServiceImpl implements JurusanService{

    private final Logger log = LoggerFactory.getLogger(JurusanServiceImpl.class);

    private final JurusanRepository jurusanRepository;

    private final JurusanMapper jurusanMapper;

    public JurusanServiceImpl(JurusanRepository jurusanRepository, JurusanMapper jurusanMapper) {
        this.jurusanRepository = jurusanRepository;
        this.jurusanMapper = jurusanMapper;
    }

    /**
     * Save a jurusan.
     *
     * @param jurusanDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public JurusanDTO save(JurusanDTO jurusanDTO) {
        log.debug("Request to save Jurusan : {}", jurusanDTO);
        Jurusan jurusan = jurusanMapper.toEntity(jurusanDTO);
        jurusan = jurusanRepository.save(jurusan);
        return jurusanMapper.toDto(jurusan);
    }

    /**
     *  Get all the jurusans.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<JurusanDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Jurusans");
        return jurusanRepository.findAll(pageable)
            .map(jurusanMapper::toDto);
    }

    /**
     *  Get one jurusan by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public JurusanDTO findOne(Long id) {
        log.debug("Request to get Jurusan : {}", id);
        Jurusan jurusan = jurusanRepository.findOne(id);
        return jurusanMapper.toDto(jurusan);
    }

    /**
     *  Delete the  jurusan by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Jurusan : {}", id);
        jurusanRepository.delete(id);
    }
}
