package io.github.semhas.service;

import io.github.semhas.domain.User;
import io.github.semhas.service.dto.MahasiswaDTO;
import io.github.semhas.service.dto.SeminarDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Mahasiswa.
 */
public interface MahasiswaService {

    /**
     * Save a mahasiswa.
     *
     * @param mahasiswaDTO the entity to save
     * @return the persisted entity
     */
    MahasiswaDTO save(MahasiswaDTO mahasiswaDTO);

    /**
     *  Get all the mahasiswas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MahasiswaDTO> findAll(Pageable pageable);
    /**
     *  Get all the MahasiswaDTO where Seminar is null.
     *
     *  @return the list of entities
     */
    List<MahasiswaDTO> findAllWhereSeminarIsNull();

    /**
     *  Get the "id" mahasiswa.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MahasiswaDTO findOne(Long id);

    /**
     *  Delete the "id" mahasiswa.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    User createMahasiswaUser(String login, String password, String firstName, String lastName, String email, String imageUrl, String langKey, String nim, Integer semester, Long jurusanId, String nomorTelepon);

    MahasiswaDTO findByUserLogin(String username);

    SeminarDTO findSeminarByMahasiswaId(Long id);
}
