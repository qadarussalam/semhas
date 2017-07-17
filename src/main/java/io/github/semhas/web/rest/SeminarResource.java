package io.github.semhas.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.semhas.service.SeminarService;
import io.github.semhas.web.rest.util.HeaderUtil;
import io.github.semhas.web.rest.util.PaginationUtil;
import io.github.semhas.service.dto.SeminarDTO;
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

/**
 * REST controller for managing Seminar.
 */
@RestController
@RequestMapping("/api")
public class SeminarResource {

    private final Logger log = LoggerFactory.getLogger(SeminarResource.class);

    private static final String ENTITY_NAME = "seminar";

    private final SeminarService seminarService;

    public SeminarResource(SeminarService seminarService) {
        this.seminarService = seminarService;
    }

    /**
     * POST  /seminars : Create a new seminar.
     *
     * @param seminarDTO the seminarDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new seminarDTO, or with status 400 (Bad Request) if the seminar has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/seminars")
    @Timed
    public ResponseEntity<SeminarDTO> createSeminar(@Valid @RequestBody SeminarDTO seminarDTO) throws URISyntaxException {
        log.debug("REST request to save Seminar : {}", seminarDTO);
        if (seminarDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new seminar cannot already have an ID")).body(null);
        }
        SeminarDTO result = seminarService.save(seminarDTO);
        return ResponseEntity.created(new URI("/api/seminars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /seminars : Updates an existing seminar.
     *
     * @param seminarDTO the seminarDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated seminarDTO,
     * or with status 400 (Bad Request) if the seminarDTO is not valid,
     * or with status 500 (Internal Server Error) if the seminarDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/seminars")
    @Timed
    public ResponseEntity<SeminarDTO> updateSeminar(@Valid @RequestBody SeminarDTO seminarDTO) throws URISyntaxException {
        log.debug("REST request to update Seminar : {}", seminarDTO);
        if (seminarDTO.getId() == null) {
            return createSeminar(seminarDTO);
        }
        SeminarDTO result = seminarService.save(seminarDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, seminarDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /seminars : get all the seminars.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of seminars in body
     */
    @GetMapping("/seminars")
    @Timed
    public ResponseEntity<List<SeminarDTO>> getAllSeminars(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Seminars");
        Page<SeminarDTO> page = seminarService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/seminars");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /seminars/:id : get the "id" seminar.
     *
     * @param id the id of the seminarDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the seminarDTO, or with status 404 (Not Found)
     */
    @GetMapping("/seminars/{id}")
    @Timed
    public ResponseEntity<SeminarDTO> getSeminar(@PathVariable Long id) {
        log.debug("REST request to get Seminar : {}", id);
        SeminarDTO seminarDTO = seminarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(seminarDTO));
    }

    /**
     * DELETE  /seminars/:id : delete the "id" seminar.
     *
     * @param id the id of the seminarDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/seminars/{id}")
    @Timed
    public ResponseEntity<Void> deleteSeminar(@PathVariable Long id) {
        log.debug("REST request to delete Seminar : {}", id);
        seminarService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
