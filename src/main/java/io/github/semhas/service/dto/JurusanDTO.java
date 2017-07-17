package io.github.semhas.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Jurusan entity.
 */
public class JurusanDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 5)
    private String nama;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JurusanDTO jurusanDTO = (JurusanDTO) o;
        if(jurusanDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jurusanDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JurusanDTO{" +
            "id=" + getId() +
            ", nama='" + getNama() + "'" +
            "}";
    }
}
