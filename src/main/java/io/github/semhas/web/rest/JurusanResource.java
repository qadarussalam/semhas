package io.github.semhas.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.semhas.service.JurusanService;
import io.github.semhas.web.rest.util.HeaderUtil;
import io.github.semhas.web.rest.util.PaginationUtil;
import io.github.semhas.service.dto.JurusanDTO;
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
 * REST controller for managing Jurusan.
 */
@RestController
@RequestMapping("/api")
public class JurusanResource {

    private final Logger log = LoggerFactory.getLogger(JurusanResource.class);

    private static final String ENTITY_NAME = "jurusan";

    private final JurusanService jurusanService;

    public JurusanResource(JurusanService jurusanService) {
        this.jurusanService = jurusanService;
    }

    /**
     * POST  /jurusans : Create a new jurusan.
     *
     * @param jurusanDTO the jurusanDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jurusanDTO, or with status 400 (Bad Request) if the jurusan has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/jurusans")
    @Timed
    public ResponseEntity<JurusanDTO> createJurusan(@Valid @RequestBody JurusanDTO jurusanDTO) throws URISyntaxException {
        log.debug("REST request to save Jurusan : {}", jurusanDTO);
        if (jurusanDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new jurusan cannot already have an ID")).body(null);
        }
        JurusanDTO result = jurusanService.save(jurusanDTO);
        return ResponseEntity.created(new URI("/api/jurusans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /jurusans : Updates an existing jurusan.
     *
     * @param jurusanDTO the jurusanDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jurusanDTO,
     * or with status 400 (Bad Request) if the jurusanDTO is not valid,
     * or with status 500 (Internal Server Error) if the jurusanDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/jurusans")
    @Timed
    public ResponseEntity<JurusanDTO> updateJurusan(@Valid @RequestBody JurusanDTO jurusanDTO) throws URISyntaxException {
        log.debug("REST request to update Jurusan : {}", jurusanDTO);
        if (jurusanDTO.getId() == null) {
            return createJurusan(jurusanDTO);
        }
        JurusanDTO result = jurusanService.save(jurusanDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jurusanDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /jurusans : get all the jurusans.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of jurusans in body
     */
    @GetMapping("/jurusans")
    @Timed
    public ResponseEntity<List<JurusanDTO>> getAllJurusans(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Jurusans");
        Page<JurusanDTO> page = jurusanService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jurusans");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /jurusans/:id : get the "id" jurusan.
     *
     * @param id the id of the jurusanDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jurusanDTO, or with status 404 (Not Found)
     */
    @GetMapping("/jurusans/{id}")
    @Timed
    public ResponseEntity<JurusanDTO> getJurusan(@PathVariable Long id) {
        log.debug("REST request to get Jurusan : {}", id);
        JurusanDTO jurusanDTO = jurusanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(jurusanDTO));
    }

    /**
     * DELETE  /jurusans/:id : delete the "id" jurusan.
     *
     * @param id the id of the jurusanDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/jurusans/{id}")
    @Timed
    public ResponseEntity<Void> deleteJurusan(@PathVariable Long id) {
        log.debug("REST request to delete Jurusan : {}", id);
        jurusanService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
