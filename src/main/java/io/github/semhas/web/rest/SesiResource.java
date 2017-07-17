package io.github.semhas.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.semhas.service.SesiService;
import io.github.semhas.web.rest.util.HeaderUtil;
import io.github.semhas.web.rest.util.PaginationUtil;
import io.github.semhas.service.dto.SesiDTO;
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
 * REST controller for managing Sesi.
 */
@RestController
@RequestMapping("/api")
public class SesiResource {

    private final Logger log = LoggerFactory.getLogger(SesiResource.class);

    private static final String ENTITY_NAME = "sesi";

    private final SesiService sesiService;

    public SesiResource(SesiService sesiService) {
        this.sesiService = sesiService;
    }

    /**
     * POST  /sesis : Create a new sesi.
     *
     * @param sesiDTO the sesiDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sesiDTO, or with status 400 (Bad Request) if the sesi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sesis")
    @Timed
    public ResponseEntity<SesiDTO> createSesi(@RequestBody SesiDTO sesiDTO) throws URISyntaxException {
        log.debug("REST request to save Sesi : {}", sesiDTO);
        if (sesiDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new sesi cannot already have an ID")).body(null);
        }
        SesiDTO result = sesiService.save(sesiDTO);
        return ResponseEntity.created(new URI("/api/sesis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sesis : Updates an existing sesi.
     *
     * @param sesiDTO the sesiDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sesiDTO,
     * or with status 400 (Bad Request) if the sesiDTO is not valid,
     * or with status 500 (Internal Server Error) if the sesiDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sesis")
    @Timed
    public ResponseEntity<SesiDTO> updateSesi(@RequestBody SesiDTO sesiDTO) throws URISyntaxException {
        log.debug("REST request to update Sesi : {}", sesiDTO);
        if (sesiDTO.getId() == null) {
            return createSesi(sesiDTO);
        }
        SesiDTO result = sesiService.save(sesiDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sesiDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sesis : get all the sesis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sesis in body
     */
    @GetMapping("/sesis")
    @Timed
    public ResponseEntity<List<SesiDTO>> getAllSesis(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Sesis");
        Page<SesiDTO> page = sesiService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sesis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sesis/:id : get the "id" sesi.
     *
     * @param id the id of the sesiDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sesiDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sesis/{id}")
    @Timed
    public ResponseEntity<SesiDTO> getSesi(@PathVariable Long id) {
        log.debug("REST request to get Sesi : {}", id);
        SesiDTO sesiDTO = sesiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sesiDTO));
    }

    /**
     * DELETE  /sesis/:id : delete the "id" sesi.
     *
     * @param id the id of the sesiDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sesis/{id}")
    @Timed
    public ResponseEntity<Void> deleteSesi(@PathVariable Long id) {
        log.debug("REST request to delete Sesi : {}", id);
        sesiService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
