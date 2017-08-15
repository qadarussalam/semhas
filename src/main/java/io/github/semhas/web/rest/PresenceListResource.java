package io.github.semhas.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import io.github.semhas.service.SeminarService;
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
public class PresenceListResource {

    private static final Logger log = LoggerFactory.getLogger(PresenceListResource.class);

    private final SeminarService seminarService;

    public PresenceListResource(SeminarService seminarService) {
        this.seminarService = seminarService;
    }

    @GetMapping("/seminars/{idseminar}/presence-checklist/printable")
    @Timed
    public ResponseEntity<String> getPrintablePresenceList(@PathVariable Long idseminar) {
        log.debug("REST request to get printable seminar {}  presence list", idseminar);
        SeminarDTO one = seminarService.findOne(idseminar);
        if (one == null) {
            return ResponseEntity.notFound().build();
        }
        String presenceListHtml = seminarService.getPrintablePresenceList(idseminar);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(presenceListHtml));
    }
}
