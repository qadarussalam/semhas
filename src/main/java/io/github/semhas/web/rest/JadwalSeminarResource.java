package io.github.semhas.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.semhas.service.JadwalSeminarService;
import io.github.semhas.web.rest.util.HeaderUtil;
import io.github.semhas.web.rest.util.PaginationUtil;
import io.github.semhas.service.dto.JadwalSeminarDTO;
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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing JadwalSeminar.
 */
@RestController
@RequestMapping("/api")
public class JadwalSeminarResource {

    private final Logger log = LoggerFactory.getLogger(JadwalSeminarResource.class);

    private static final String ENTITY_NAME = "jadwalSeminar";

    private final JadwalSeminarService jadwalSeminarService;

    public JadwalSeminarResource(JadwalSeminarService jadwalSeminarService) {
        this.jadwalSeminarService = jadwalSeminarService;
    }

    /**
     * POST  /jadwal-seminars : Create a new jadwalSeminar.
     *
     * @param jadwalSeminarDTO the jadwalSeminarDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jadwalSeminarDTO, or with status 400 (Bad Request) if the jadwalSeminar has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/jadwal-seminars")
    @Timed
    public ResponseEntity<JadwalSeminarDTO> createJadwalSeminar(@RequestBody JadwalSeminarDTO jadwalSeminarDTO) throws URISyntaxException {
        log.debug("REST request to save JadwalSeminar : {}", jadwalSeminarDTO);
        if (jadwalSeminarDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new jadwalSeminar cannot already have an ID")).body(null);
        }
        JadwalSeminarDTO result = jadwalSeminarService.save(jadwalSeminarDTO);
        return ResponseEntity.created(new URI("/api/jadwal-seminars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /jadwal-seminars : Updates an existing jadwalSeminar.
     *
     * @param jadwalSeminarDTO the jadwalSeminarDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jadwalSeminarDTO,
     * or with status 400 (Bad Request) if the jadwalSeminarDTO is not valid,
     * or with status 500 (Internal Server Error) if the jadwalSeminarDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/jadwal-seminars")
    @Timed
    public ResponseEntity<JadwalSeminarDTO> updateJadwalSeminar(@RequestBody JadwalSeminarDTO jadwalSeminarDTO) throws URISyntaxException {
        log.debug("REST request to update JadwalSeminar : {}", jadwalSeminarDTO);
        if (jadwalSeminarDTO.getId() == null) {
            return createJadwalSeminar(jadwalSeminarDTO);
        }
        JadwalSeminarDTO result = jadwalSeminarService.save(jadwalSeminarDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jadwalSeminarDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /jadwal-seminars : get all the jadwalSeminars.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request {seminar-is-null, tersedia, not-tersedia}
     * @return the ResponseEntity with status 200 (OK) and the list of jadwalSeminars in body
     */
    @GetMapping("/jadwal-seminars")
    @Timed
    public ResponseEntity<List<JadwalSeminarDTO>> getAllJadwalSeminars(@ApiParam Pageable pageable, @RequestParam(required = false) String filter) {
        if ("seminar-is-null".equals(filter)) {
            log.debug("REST request to get all JadwalSeminars where seminar is null");
            return new ResponseEntity<>(jadwalSeminarService.findAllWhereSeminarIsNull(),
                    HttpStatus.OK);
        } else if ("tersedia".equals(filter)) {
            log.debug("REST request to get all jadwalSeminars where tersedia is true");
            return new ResponseEntity<>(jadwalSeminarService.findAllWhereTersedia(), HttpStatus.OK);
        } else if ("not-tersedia".equals(filter)) {
            log.debug("REST request to get all jadwalSeminars where tersedia is false");
            return new ResponseEntity<>(jadwalSeminarService.findAllWhereNotTersedia(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of JadwalSeminars");
        Page<JadwalSeminarDTO> page = jadwalSeminarService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jadwal-seminars");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /jadwal-seminars/:id : get the "id" jadwalSeminar.
     *
     * @param id the id of the jadwalSeminarDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jadwalSeminarDTO, or with status 404 (Not Found)
     */
    @GetMapping("/jadwal-seminars/{id}")
    @Timed
    public ResponseEntity<JadwalSeminarDTO> getJadwalSeminar(@PathVariable Long id) {
        log.debug("REST request to get JadwalSeminar : {}", id);
        JadwalSeminarDTO jadwalSeminarDTO = jadwalSeminarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(jadwalSeminarDTO));
    }

    /**
     * DELETE  /jadwal-seminars/:id : delete the "id" jadwalSeminar.
     *
     * @param id the id of the jadwalSeminarDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/jadwal-seminars/{id}")
    @Timed
    public ResponseEntity<Void> deleteJadwalSeminar(@PathVariable Long id) {
        log.debug("REST request to delete JadwalSeminar : {}", id);
        jadwalSeminarService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
