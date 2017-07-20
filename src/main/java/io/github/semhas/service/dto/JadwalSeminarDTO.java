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

    private String sesiUrutan;

    private Long ruangId;

    private String ruangNama;

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

    public String getSesiUrutan() {
        return sesiUrutan;
    }

    public void setSesiUrutan(String sesiUrutan) {
        this.sesiUrutan = sesiUrutan;
    }

    public Long getRuangId() {
        return ruangId;
    }

    public void setRuangId(Long ruangId) {
        this.ruangId = ruangId;
    }

    public String getRuangNama() {
        return ruangNama;
    }

    public void setRuangNama(String ruangNama) {
        this.ruangNama = ruangNama;
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
