package io.github.semhas.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Sesi entity.
 */
public class SesiDTO implements Serializable {

    private Long id;

    private Integer urutan;

    private ZonedDateTime jamMulai;

    private ZonedDateTime jamSelesai;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUrutan() {
        return urutan;
    }

    public void setUrutan(Integer urutan) {
        this.urutan = urutan;
    }

    public ZonedDateTime getJamMulai() {
        return jamMulai;
    }

    public void setJamMulai(ZonedDateTime jamMulai) {
        this.jamMulai = jamMulai;
    }

    public ZonedDateTime getJamSelesai() {
        return jamSelesai;
    }

    public void setJamSelesai(ZonedDateTime jamSelesai) {
        this.jamSelesai = jamSelesai;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SesiDTO sesiDTO = (SesiDTO) o;
        if(sesiDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sesiDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SesiDTO{" +
            "id=" + getId() +
            ", urutan='" + getUrutan() + "'" +
            ", jamMulai='" + getJamMulai() + "'" +
            ", jamSelesai='" + getJamSelesai() + "'" +
            "}";
    }
}
