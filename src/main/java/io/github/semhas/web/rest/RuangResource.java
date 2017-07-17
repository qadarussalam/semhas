package io.github.semhas.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.semhas.service.RuangService;
import io.github.semhas.web.rest.util.HeaderUtil;
import io.github.semhas.web.rest.util.PaginationUtil;
import io.github.semhas.service.dto.RuangDTO;
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
 * REST controller for managing Ruang.
 */
@RestController
@RequestMapping("/api")
public class RuangResource {

    private final Logger log = LoggerFactory.getLogger(RuangResource.class);

    private static final String ENTITY_NAME = "ruang";

    private final RuangService ruangService;

    public RuangResource(RuangService ruangService) {
        this.ruangService = ruangService;
    }

    /**
     * POST  /ruangs : Create a new ruang.
     *
     * @param ruangDTO the ruangDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ruangDTO, or with status 400 (Bad Request) if the ruang has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ruangs")
    @Timed
    public ResponseEntity<RuangDTO> createRuang(@RequestBody RuangDTO ruangDTO) throws URISyntaxException {
        log.debug("REST request to save Ruang : {}", ruangDTO);
        if (ruangDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ruang cannot already have an ID")).body(null);
        }
        RuangDTO result = ruangService.save(ruangDTO);
        return ResponseEntity.created(new URI("/api/ruangs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ruangs : Updates an existing ruang.
     *
     * @param ruangDTO the ruangDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ruangDTO,
     * or with status 400 (Bad Request) if the ruangDTO is not valid,
     * or with status 500 (Internal Server Error) if the ruangDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ruangs")
    @Timed
    public ResponseEntity<RuangDTO> updateRuang(@RequestBody RuangDTO ruangDTO) throws URISyntaxException {
        log.debug("REST request to update Ruang : {}", ruangDTO);
        if (ruangDTO.getId() == null) {
            return createRuang(ruangDTO);
        }
        RuangDTO result = ruangService.save(ruangDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ruangDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ruangs : get all the ruangs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ruangs in body
     */
    @GetMapping("/ruangs")
    @Timed
    public ResponseEntity<List<RuangDTO>> getAllRuangs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Ruangs");
        Page<RuangDTO> page = ruangService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ruangs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ruangs/:id : get the "id" ruang.
     *
     * @param id the id of the ruangDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ruangDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ruangs/{id}")
    @Timed
    public ResponseEntity<RuangDTO> getRuang(@PathVariable Long id) {
        log.debug("REST request to get Ruang : {}", id);
        RuangDTO ruangDTO = ruangService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ruangDTO));
    }

    /**
     * DELETE  /ruangs/:id : delete the "id" ruang.
     *
     * @param id the id of the ruangDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ruangs/{id}")
    @Timed
    public ResponseEntity<Void> deleteRuang(@PathVariable Long id) {
        log.debug("REST request to delete Ruang : {}", id);
        ruangService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
