package io.github.semhas.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.semhas.service.MahasiswaService;
import io.github.semhas.web.rest.util.HeaderUtil;
import io.github.semhas.web.rest.util.PaginationUtil;
import io.github.semhas.service.dto.MahasiswaDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Mahasiswa.
 */
@RestController
@RequestMapping("/api")
public class MahasiswaResource {

    private final Logger log = LoggerFactory.getLogger(MahasiswaResource.class);

    private static final String ENTITY_NAME = "mahasiswa";

    private final MahasiswaService mahasiswaService;

    public MahasiswaResource(MahasiswaService mahasiswaService) {
        this.mahasiswaService = mahasiswaService;
    }

    /**
     * POST  /mahasiswas : Create a new mahasiswa.
     *
     * @param mahasiswaDTO the mahasiswaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mahasiswaDTO, or with status 400 (Bad Request) if the mahasiswa has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mahasiswas")
    @Timed
    public ResponseEntity<MahasiswaDTO> createMahasiswa(@Valid @RequestBody MahasiswaDTO mahasiswaDTO) throws URISyntaxException {
        log.debug("REST request to save Mahasiswa : {}", mahasiswaDTO);
        if (mahasiswaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new mahasiswa cannot already have an ID")).body(null);
        }
        MahasiswaDTO result = mahasiswaService.save(mahasiswaDTO);
        return ResponseEntity.created(new URI("/api/mahasiswas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mahasiswas : Updates an existing mahasiswa.
     *
     * @param mahasiswaDTO the mahasiswaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mahasiswaDTO,
     * or with status 400 (Bad Request) if the mahasiswaDTO is not valid,
     * or with status 500 (Internal Server Error) if the mahasiswaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mahasiswas")
    @Timed
    public ResponseEntity<MahasiswaDTO> updateMahasiswa(@Valid @RequestBody MahasiswaDTO mahasiswaDTO) throws URISyntaxException {
        log.debug("REST request to update Mahasiswa : {}", mahasiswaDTO);
        if (mahasiswaDTO.getId() == null) {
            return createMahasiswa(mahasiswaDTO);
        }
        MahasiswaDTO result = mahasiswaService.save(mahasiswaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mahasiswaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mahasiswas : get all the mahasiswas.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of mahasiswas in body
     */
    @GetMapping("/mahasiswas")
    @Timed
    public ResponseEntity<List<MahasiswaDTO>> getAllMahasiswas(@ApiParam Pageable pageable, @RequestParam(required = false) String filter) {
        if ("seminar-is-null".equals(filter)) {
            log.debug("REST request to get all Mahasiswas where seminar is null");
            return new ResponseEntity<>(mahasiswaService.findAllWhereSeminarIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of Mahasiswas");
        Page<MahasiswaDTO> page = mahasiswaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mahasiswas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mahasiswas/:id : get the "id" mahasiswa.
     *
     * @param id the id of the mahasiswaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mahasiswaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mahasiswas/{id}")
    @Timed
    public ResponseEntity<MahasiswaDTO> getMahasiswa(@PathVariable Long id) {
        log.debug("REST request to get Mahasiswa : {}", id);
        MahasiswaDTO mahasiswaDTO = mahasiswaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mahasiswaDTO));
    }

    /**
     * DELETE  /mahasiswas/:id : delete the "id" mahasiswa.
     *
     * @param id the id of the mahasiswaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mahasiswas/{id}")
    @Timed
    public ResponseEntity<Void> deleteMahasiswa(@PathVariable Long id) {
        log.debug("REST request to delete Mahasiswa : {}", id);
        mahasiswaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
