package io.github.semhas.service.impl;

import io.github.semhas.service.JadwalSeminarService;
import io.github.semhas.domain.JadwalSeminar;
import io.github.semhas.repository.JadwalSeminarRepository;
import io.github.semhas.service.dto.JadwalSeminarDTO;
import io.github.semhas.service.mapper.JadwalSeminarMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing JadwalSeminar.
 */
@Service
@Transactional
public class JadwalSeminarServiceImpl implements JadwalSeminarService{

    private final Logger log = LoggerFactory.getLogger(JadwalSeminarServiceImpl.class);

    private final JadwalSeminarRepository jadwalSeminarRepository;

    private final JadwalSeminarMapper jadwalSeminarMapper;

    public JadwalSeminarServiceImpl(JadwalSeminarRepository jadwalSeminarRepository, JadwalSeminarMapper jadwalSeminarMapper) {
        this.jadwalSeminarRepository = jadwalSeminarRepository;
        this.jadwalSeminarMapper = jadwalSeminarMapper;
    }

    /**
     * Save a jadwalSeminar.
     *
     * @param jadwalSeminarDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public JadwalSeminarDTO save(JadwalSeminarDTO jadwalSeminarDTO) {
        log.debug("Request to save JadwalSeminar : {}", jadwalSeminarDTO);
        JadwalSeminar jadwalSeminar = jadwalSeminarMapper.toEntity(jadwalSeminarDTO);
        jadwalSeminar = jadwalSeminarRepository.save(jadwalSeminar);
        return jadwalSeminarMapper.toDto(jadwalSeminar);
    }

    /**
     *  Get all the jadwalSeminars.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<JadwalSeminarDTO> findAll(Pageable pageable) {
        log.debug("Request to get all JadwalSeminars");
        return jadwalSeminarRepository.findAll(pageable)
            .map(jadwalSeminarMapper::toDto);
    }


    /**
     *  get all the jadwalSeminars where Seminar is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<JadwalSeminarDTO> findAllWhereSeminarIsNull() {
        log.debug("Request to get all jadwalSeminars where Seminar is null");
        return StreamSupport
            .stream(jadwalSeminarRepository.findAll().spliterator(), false)
            .filter(jadwalSeminar -> jadwalSeminar.getSeminar() == null)
            .map(jadwalSeminarMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one jadwalSeminar by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public JadwalSeminarDTO findOne(Long id) {
        log.debug("Request to get JadwalSeminar : {}", id);
        JadwalSeminar jadwalSeminar = jadwalSeminarRepository.findOne(id);
        return jadwalSeminarMapper.toDto(jadwalSeminar);
    }

    /**
     *  Delete the  jadwalSeminar by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete JadwalSeminar : {}", id);
        jadwalSeminarRepository.delete(id);
    }

    @Override
    public List<JadwalSeminarDTO> findAllWhereTersedia() {
        log.debug("Request to get all jadwalSeminars where tersedia is true");
        return StreamSupport
            .stream(jadwalSeminarRepository.findAll().spliterator(), false)
            .filter(jadwalSeminar -> jadwalSeminar.isTersedia())
            .map(jadwalSeminarMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<JadwalSeminarDTO> findAllWhereNotTersedia() {
        log.debug("Request to get all jadwalSeminars where tersedia is false");
        return StreamSupport
            .stream(jadwalSeminarRepository.findAll().spliterator(), false)
            .filter(jadwalSeminar -> !jadwalSeminar.isTersedia())
            .map(jadwalSeminarMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
