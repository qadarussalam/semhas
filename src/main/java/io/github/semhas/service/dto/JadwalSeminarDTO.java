package io.github.semhas.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the JadwalSeminar entity.
 */
public class JadwalSeminarDTO implements Serializable {

    private Long id;

    private ZonedDateTime tanggal;

    private Boolean tersedia;

    private Long sesiId;

    private Long ruangId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getTanggal() {
        return tanggal;
    }

    public void setTanggal(ZonedDateTime tanggal) {
        this.tanggal = tanggal;
    }

    public Boolean isTersedia() {
        return tersedia;
    }

    public void setTersedia(Boolean tersedia) {
        this.tersedia = tersedia;
    }

    public Long getSesiId() {
        return sesiId;
    }

    public void setSesiId(Long sesiId) {
        this.sesiId = sesiId;
    }

    public Long getRuangId() {
        return ruangId;
    }

    public void setRuangId(Long ruangId) {
        this.ruangId = ruangId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JadwalSeminarDTO jadwalSeminarDTO = (JadwalSeminarDTO) o;
        if(jadwalSeminarDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jadwalSeminarDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JadwalSeminarDTO{" +
            "id=" + getId() +
            ", tanggal='" + getTanggal() + "'" +
            ", tersedia='" + isTersedia() + "'" +
            "}";
    }
}
