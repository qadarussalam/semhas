package io.github.semhas.service.impl;

import io.github.semhas.service.MahasiswaService;
import io.github.semhas.domain.Mahasiswa;
import io.github.semhas.repository.MahasiswaRepository;
import io.github.semhas.service.dto.MahasiswaDTO;
import io.github.semhas.service.mapper.MahasiswaMapper;
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
 * Service Implementation for managing Mahasiswa.
 */
@Service
@Transactional
public class MahasiswaServiceImpl implements MahasiswaService{

    private final Logger log = LoggerFactory.getLogger(MahasiswaServiceImpl.class);

    private final MahasiswaRepository mahasiswaRepository;

    private final MahasiswaMapper mahasiswaMapper;

    public MahasiswaServiceImpl(MahasiswaRepository mahasiswaRepository, MahasiswaMapper mahasiswaMapper) {
        this.mahasiswaRepository = mahasiswaRepository;
        this.mahasiswaMapper = mahasiswaMapper;
    }

    /**
     * Save a mahasiswa.
     *
     * @param mahasiswaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MahasiswaDTO save(MahasiswaDTO mahasiswaDTO) {
        log.debug("Request to save Mahasiswa : {}", mahasiswaDTO);
        Mahasiswa mahasiswa = mahasiswaMapper.toEntity(mahasiswaDTO);
        mahasiswa = mahasiswaRepository.save(mahasiswa);
        return mahasiswaMapper.toDto(mahasiswa);
    }

    /**
     *  Get all the mahasiswas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MahasiswaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Mahasiswas");
        return mahasiswaRepository.findAll(pageable)
            .map(mahasiswaMapper::toDto);
    }


    /**
     *  get all the mahasiswas where Seminar is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<MahasiswaDTO> findAllWhereSeminarIsNull() {
        log.debug("Request to get all mahasiswas where Seminar is null");
        return StreamSupport
            .stream(mahasiswaRepository.findAll().spliterator(), false)
            .filter(mahasiswa -> mahasiswa.getSeminar() == null)
            .map(mahasiswaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one mahasiswa by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MahasiswaDTO findOne(Long id) {
        log.debug("Request to get Mahasiswa : {}", id);
        Mahasiswa mahasiswa = mahasiswaRepository.findOne(id);
        return mahasiswaMapper.toDto(mahasiswa);
    }

    /**
     *  Delete the  mahasiswa by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Mahasiswa : {}", id);
        mahasiswaRepository.delete(id);
    }
}
