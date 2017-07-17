package io.github.semhas.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.semhas.service.PesertaSeminarService;
import io.github.semhas.web.rest.util.HeaderUtil;
import io.github.semhas.web.rest.util.PaginationUtil;
import io.github.semhas.service.dto.PesertaSeminarDTO;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PesertaSeminar.
 */
@RestController
@RequestMapping("/api")
public class PesertaSeminarResource {

    private final Logger log = LoggerFactory.getLogger(PesertaSeminarResource.class);

    private static final String ENTITY_NAME = "pesertaSeminar";

    private final PesertaSeminarService pesertaSeminarService;

    public PesertaSeminarResource(PesertaSeminarService pesertaSeminarService) {
        this.pesertaSeminarService = pesertaSeminarService;
    }

    /**
     * POST  /peserta-seminars : Create a new pesertaSeminar.
     *
     * @param pesertaSeminarDTO the pesertaSeminarDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pesertaSeminarDTO, or with status 400 (Bad Request) if the pesertaSeminar has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/peserta-seminars")
    @Timed
    public ResponseEntity<PesertaSeminarDTO> createPesertaSeminar(@RequestBody PesertaSeminarDTO pesertaSeminarDTO) throws URISyntaxException {
        log.debug("REST request to save PesertaSeminar : {}", pesertaSeminarDTO);
        if (pesertaSeminarDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pesertaSeminar cannot already have an ID")).body(null);
        }
        PesertaSeminarDTO result = pesertaSeminarService.save(pesertaSeminarDTO);
        return ResponseEntity.created(new URI("/api/peserta-seminars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /peserta-seminars : Updates an existing pesertaSeminar.
     *
     * @param pesertaSeminarDTO the pesertaSeminarDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pesertaSeminarDTO,
     * or with status 400 (Bad Request) if the pesertaSeminarDTO is not valid,
     * or with status 500 (Internal Server Error) if the pesertaSeminarDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/peserta-seminars")
    @Timed
    public ResponseEntity<PesertaSeminarDTO> updatePesertaSeminar(@RequestBody PesertaSeminarDTO pesertaSeminarDTO) throws URISyntaxException {
        log.debug("REST request to update PesertaSeminar : {}", pesertaSeminarDTO);
        if (pesertaSeminarDTO.getId() == null) {
            return createPesertaSeminar(pesertaSeminarDTO);
        }
        PesertaSeminarDTO result = pesertaSeminarService.save(pesertaSeminarDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pesertaSeminarDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /peserta-seminars : get all the pesertaSeminars.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pesertaSeminars in body
     */
    @GetMapping("/peserta-seminars")
    @Timed
    public ResponseEntity<List<PesertaSeminarDTO>> getAllPesertaSeminars(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of PesertaSeminars");
        Page<PesertaSeminarDTO> page = pesertaSeminarService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/peserta-seminars");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /peserta-seminars/:id : get the "id" pesertaSeminar.
     *
     * @param id the id of the pesertaSeminarDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pesertaSeminarDTO, or with status 404 (Not Found)
     */
    @GetMapping("/peserta-seminars/{id}")
    @Timed
    public ResponseEntity<PesertaSeminarDTO> getPesertaSeminar(@PathVariable Long id) {
        log.debug("REST request to get PesertaSeminar : {}", id);
        PesertaSeminarDTO pesertaSeminarDTO = pesertaSeminarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pesertaSeminarDTO));
    }

    /**
     * DELETE  /peserta-seminars/:id : delete the "id" pesertaSeminar.
     *
     * @param id the id of the pesertaSeminarDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/peserta-seminars/{id}")
    @Timed
    public ResponseEntity<Void> deletePesertaSeminar(@PathVariable Long id) {
        log.debug("REST request to delete PesertaSeminar : {}", id);
        pesertaSeminarService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
