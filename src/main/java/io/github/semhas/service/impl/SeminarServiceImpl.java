package io.github.semhas.service.impl;

import io.github.semhas.domain.JadwalSeminar;
import io.github.semhas.domain.Mahasiswa;
import io.github.semhas.domain.PesertaSeminar;
import io.github.semhas.domain.Seminar;
import io.github.semhas.domain.enumeration.StatusSeminar;
import io.github.semhas.repository.SeminarRepository;
import io.github.semhas.service.JadwalSeminarService;
import io.github.semhas.service.MailService;
import io.github.semhas.service.SeminarService;
import io.github.semhas.service.dto.JadwalSeminarDTO;
import io.github.semhas.service.dto.PesertaSeminarDTO;
import io.github.semhas.service.dto.SeminarDTO;
import io.github.semhas.service.mapper.PesertaSeminarMapper;
import io.github.semhas.service.mapper.SeminarMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * Service Implementation for managing Seminar.
 */
@Service
@Transactional
public class SeminarServiceImpl implements SeminarService{

    private final Logger log = LoggerFactory.getLogger(SeminarServiceImpl.class);

    private final SeminarRepository seminarRepository;

    private final SeminarMapper seminarMapper;

    private final JadwalSeminarService jadwalSeminarService;

    private final PesertaSeminarMapper pesertaSeminarMapper;

    private final MailService mailService;

    public SeminarServiceImpl(SeminarRepository seminarRepository, SeminarMapper seminarMapper, JadwalSeminarService jadwalSeminarService, PesertaSeminarMapper pesertaSeminarMapper, MailService mailService) {
        this.seminarRepository = seminarRepository;
        this.seminarMapper = seminarMapper;
        this.jadwalSeminarService = jadwalSeminarService;
        this.pesertaSeminarMapper = pesertaSeminarMapper;
        this.mailService = mailService;
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
        Seminar seminar = null;

        if (seminarDTO.getId() != null) {
            seminar = seminarRepository.findOne(seminarDTO.getId());
            JadwalSeminar existingJadwal = seminar.getJadwalSeminar();
            if (existingJadwal != null && !existingJadwal.getId().equals(seminarDTO.getJadwalSeminarId())) {
                existingJadwal.setTersedia(true);
            }
        }

        seminar = seminarMapper.toEntity(seminarDTO);

        JadwalSeminar jadwalSeminar = seminar.getJadwalSeminar();
        if (jadwalSeminar != null) {
            JadwalSeminarDTO jadwal = jadwalSeminarService.findOne(jadwalSeminar.getId());
            if (jadwal != null) {
                seminar.setRuangan(jadwal.getRuangNama());
                jadwal.setTersedia(false);
                jadwalSeminarService.save(jadwal);
            } else {
                seminar.setRuangan(null);
            }
        } else {
            seminar.setRuangan(null);
        }
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

    @Override
    public Page<SeminarDTO> searchByJudul(String query, Pageable pageable) {
        log.debug("Request to search Seminar by judul like {}", query);
        return seminarRepository.findAllByJudulContains(query, pageable)
            .map(seminarMapper::toDto);
    }

    @Override
    public Page<SeminarDTO> findAllByStatus(StatusSeminar status, Pageable pageable) {
        log.debug("Request to all Seminar by status {}", status);
        return seminarRepository.findAllByStatus(status, pageable)
            .map(seminarMapper::toDto);
    }

    @Override
    public Page<SeminarDTO> findAllByStatusAndDosenId(StatusSeminar status, Long dosenId, Pageable pageable) {
        log.debug("Request to all Seminar by status {} and dosenId {}", status, dosenId);
        return seminarRepository.findAllByStatusAndDosenPertamaIdOrDosenKeduaId(status, dosenId, dosenId, pageable)
            .map(seminarMapper::toDto);
    }

    @Override
    public Page<SeminarDTO> findAllByDosenId(Long dosenId, Pageable pageable) {
        log.debug("Request to all Seminar by dosenId {}", dosenId);
        return seminarRepository.findAllByDosenPertamaIdOrDosenKeduaId(dosenId, dosenId, pageable)
            .map(seminarMapper::toDto);
    }

    @Override
    public List<PesertaSeminarDTO> findPesertaSeminar(Long id) {
        Seminar one = seminarRepository.findOne(id);
        if (one != null) {
            List<PesertaSeminarDTO> result = new ArrayList<>();
            for (PesertaSeminar pesertaSeminar : one.getListPesertaSeminars()) {
                result.add(pesertaSeminarMapper.toDto(pesertaSeminar));
            }
            return result;
        }
        return null;
    }

    /**
     *
     * <p>
     * This is scheduled to get fired everyday, at 06:00 (am).
     */
    @Scheduled(cron = "0 0 6 * * ?")
    @Override
    public void sendSeminarReminderEmailNotification() {
        ZonedDateTime now = ZonedDateTime.now();
        List<Seminar> seminars = seminarRepository.findAllByJamMulaiBetween(now, now.plusMinutes(1439));
        for (Seminar seminar : seminars) {
            for (PesertaSeminar pesertaSeminar : seminar.getListPesertaSeminars()) {
                if (pesertaSeminar.getMahasiswa() != null && pesertaSeminar.getMahasiswa().getUser() != null) {
                    mailService.sendSeminarReminderNotificationEmail(pesertaSeminar.getMahasiswa().getUser(), seminar);
                }
            }
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Page<SeminarDTO> searchByJudulAndUserNotRegistered(String query, Long idMahasiswa, Pageable pageable) {
        log.debug("Request to search Seminar by judul like {} and not registered by {}", query, idMahasiswa);
        Mahasiswa mahasiswa = new Mahasiswa(idMahasiswa);
        query = "%" + query + "%";
        return seminarRepository.findAllByJudulContainsAndListPesertaSeminarsMahasiswaNot(query, mahasiswa, pageable)
            .map(seminarMapper::toDto);
    }
}
