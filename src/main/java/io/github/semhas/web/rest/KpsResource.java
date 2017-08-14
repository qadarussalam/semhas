package io.github.semhas.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import io.github.semhas.service.KpsService;
import io.github.semhas.service.MahasiswaService;
import io.github.semhas.service.dto.KpsDTO;
import io.github.semhas.service.dto.MahasiswaDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class KpsResource {
    private static final Logger log = LoggerFactory.getLogger(KpsResource.class);

    private final MahasiswaService mahasiswaService;
    private final KpsService kpsService;

    public KpsResource(MahasiswaService mahasiswaService, KpsService kpsService) {
        this.mahasiswaService = mahasiswaService;
        this.kpsService = kpsService;
    }

    @GetMapping("/mahasiswas/{id}/kps")
    @Timed
    public ResponseEntity<KpsDTO> findKpsMahasiswa(@PathVariable Long id) {
        log.debug("REST request to get kps of mahasiswa {}", id);
        MahasiswaDTO one = mahasiswaService.findOne(id);
        if (one == null) {
            return ResponseEntity.notFound().build();
        }
        KpsDTO kps = mahasiswaService.findKpsMahasiswa(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(kps));
    }

    @GetMapping(value = "/mahasiswas/{id}/kps/printable", produces = {MediaType.TEXT_HTML_VALUE})
    @Timed
    public ResponseEntity<String> printKpsMahasiswa(@PathVariable Long id) {
        log.debug("REST request to get printable kps of mahasiswa {}", id);
        MahasiswaDTO one = mahasiswaService.findOne(id);
        if (one == null) {
            return ResponseEntity.notFound().build();
        }
        String kps = kpsService.getPrintableKpsMahasiswa(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(kps));
    }
}
