package io.github.semhas.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Ruang entity.
 */
public class RuangDTO implements Serializable {

    private Long id;

    private String nama;

    private Integer kapasitas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Integer getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(Integer kapasitas) {
        this.kapasitas = kapasitas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RuangDTO ruangDTO = (RuangDTO) o;
        if(ruangDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ruangDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RuangDTO{" +
            "id=" + getId() +
            ", nama='" + getNama() + "'" +
            ", kapasitas='" + getKapasitas() + "'" +
            "}";
    }
}
