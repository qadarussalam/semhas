package io.github.semhas.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import io.github.semhas.service.MahasiswaService;
import io.github.semhas.service.dto.MahasiswaDTO;
import io.github.semhas.service.dto.SeminarDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class SeminarMahasiswaResource {

    private static Logger log = LoggerFactory.getLogger(SeminarMahasiswaResource.class);

    private final MahasiswaService mahasiswaService;

    public SeminarMahasiswaResource(MahasiswaService mahasiswaService) {
        this.mahasiswaService = mahasiswaService;
    }

    /**
     * GET  /mahasiswas/:id/seminar : get the seminar of a mahasiswa.
     *
     * @param id the id of the mahasiswa seminarDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the seminarDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mahasiswas/{id}/seminar")
    @Timed
    public ResponseEntity<SeminarDTO> getSeminarMahasiswa(@PathVariable Long id) {
        log.debug("REST request to get seminar of Mahasiswa : {}", id);
        SeminarDTO seminarDTO = mahasiswaService.findSeminarByMahasiswaId(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(seminarDTO));
    }
}
