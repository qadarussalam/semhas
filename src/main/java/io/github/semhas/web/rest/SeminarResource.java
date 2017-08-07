package io.github.semhas.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import io.github.semhas.domain.enumeration.StatusSeminar;
import io.github.semhas.service.SeminarService;
import io.github.semhas.service.dto.PesertaSeminarDTO;
import io.github.semhas.service.dto.SeminarDTO;
import io.github.semhas.web.rest.util.HeaderUtil;
import io.github.semhas.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
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

    @GetMapping(value = "/seminars", params = {"q"})
    @Timed
    public ResponseEntity<List<SeminarDTO>> searchSeminar(@RequestParam(value = "q", required = true) String query,
                                                          @RequestParam(value = "not-registered-by", required = false) Long idMahasiswa,
                                                          @ApiParam Pageable pageable) {
        log.debug("REST request to search Seminar with keyword : {} and not-registered-by {}", query, idMahasiswa);
        Page<SeminarDTO> page;
        String queryParam = "?q=" + query;
        if (idMahasiswa == null) {
            page = seminarService.searchByJudul(query, pageable);
        } else {
            page = seminarService.searchByJudulAndUserNotRegistered(query, idMahasiswa, pageable);
            queryParam += "&not-registered-by=" + idMahasiswa;
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/seminars" + queryParam);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping(value = "/seminars", params = {"status","dosenId"})
    @Timed
    public ResponseEntity<List<SeminarDTO>> findSeminarBy(@RequestParam(required = false) StatusSeminar status,
                                                          @RequestParam(required = false) Long dosenId, @ApiParam Pageable pageable) {
        log.debug("REST request to search Seminar with status {} and dosenId {}", status, dosenId);
        Page<SeminarDTO> page = null;
        String urlParams = "";
        if (status != null) {
            if (dosenId == null) {
                page = seminarService.findAllByStatus(status, pageable);
                urlParams += "status=" + status.name() + "&dosenId=";
            } else {
                page = seminarService.findAllByStatusAndDosenId(status, dosenId, pageable);
                urlParams += "status=" + status.name() + "&dosenId=" + dosenId;
            }
        } else {
            if (dosenId != null) {
                page = seminarService.findAllByDosenId(dosenId, pageable);
                urlParams += "dosenId=" + dosenId + "&status=";
            } else {
                page = seminarService.findAll(pageable);
                urlParams += "dosenId=" + "&status=";
            }
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/seminars?" + urlParams);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /seminars/:id/pesertas : get the list of perserta of "id" seminar.
     *
     * @param id the id of the seminarDTO peserta to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the list of PesertaSeminarDTO, or with status 404 (Not Found)
     */
    @GetMapping("/seminars/{id}/pesertas")
    @Timed
    public ResponseEntity<List<PesertaSeminarDTO>> getPesertaSeminar(@PathVariable Long id) {
        log.debug("REST request to get list of peserta of Seminar : {}", id);
        List<PesertaSeminarDTO> result = seminarService.findPesertaSeminar(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }
}
