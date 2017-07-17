package io.github.semhas.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.semhas.service.DosenService;
import io.github.semhas.web.rest.util.HeaderUtil;
import io.github.semhas.web.rest.util.PaginationUtil;
import io.github.semhas.service.dto.DosenDTO;
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
 * REST controller for managing Dosen.
 */
@RestController
@RequestMapping("/api")
public class DosenResource {

    private final Logger log = LoggerFactory.getLogger(DosenResource.class);

    private static final String ENTITY_NAME = "dosen";

    private final DosenService dosenService;

    public DosenResource(DosenService dosenService) {
        this.dosenService = dosenService;
    }

    /**
     * POST  /dosens : Create a new dosen.
     *
     * @param dosenDTO the dosenDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dosenDTO, or with status 400 (Bad Request) if the dosen has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dosens")
    @Timed
    public ResponseEntity<DosenDTO> createDosen(@Valid @RequestBody DosenDTO dosenDTO) throws URISyntaxException {
        log.debug("REST request to save Dosen : {}", dosenDTO);
        if (dosenDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new dosen cannot already have an ID")).body(null);
        }
        DosenDTO result = dosenService.save(dosenDTO);
        return ResponseEntity.created(new URI("/api/dosens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dosens : Updates an existing dosen.
     *
     * @param dosenDTO the dosenDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dosenDTO,
     * or with status 400 (Bad Request) if the dosenDTO is not valid,
     * or with status 500 (Internal Server Error) if the dosenDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dosens")
    @Timed
    public ResponseEntity<DosenDTO> updateDosen(@Valid @RequestBody DosenDTO dosenDTO) throws URISyntaxException {
        log.debug("REST request to update Dosen : {}", dosenDTO);
        if (dosenDTO.getId() == null) {
            return createDosen(dosenDTO);
        }
        DosenDTO result = dosenService.save(dosenDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dosenDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dosens : get all the dosens.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dosens in body
     */
    @GetMapping("/dosens")
    @Timed
    public ResponseEntity<List<DosenDTO>> getAllDosens(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Dosens");
        Page<DosenDTO> page = dosenService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dosens");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dosens/:id : get the "id" dosen.
     *
     * @param id the id of the dosenDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dosenDTO, or with status 404 (Not Found)
     */
    @GetMapping("/dosens/{id}")
    @Timed
    public ResponseEntity<DosenDTO> getDosen(@PathVariable Long id) {
        log.debug("REST request to get Dosen : {}", id);
        DosenDTO dosenDTO = dosenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dosenDTO));
    }

    /**
     * DELETE  /dosens/:id : delete the "id" dosen.
     *
     * @param id the id of the dosenDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dosens/{id}")
    @Timed
    public ResponseEntity<Void> deleteDosen(@PathVariable Long id) {
        log.debug("REST request to delete Dosen : {}", id);
        dosenService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
