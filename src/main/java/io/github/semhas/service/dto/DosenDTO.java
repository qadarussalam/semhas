package io.github.semhas.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Dosen entity.
 */
public class DosenDTO implements Serializable {

    private Long id;

    @NotNull
    private String nama;

    @NotNull
    private String nip;

    @NotNull
    private String email;

    @NotNull
    private String nomorTelepon;

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

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DosenDTO dosenDTO = (DosenDTO) o;
        if(dosenDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dosenDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DosenDTO{" +
            "id=" + getId() +
            ", nama='" + getNama() + "'" +
            ", nip='" + getNip() + "'" +
            ", email='" + getEmail() + "'" +
            ", nomorTelepon='" + getNomorTelepon() + "'" +
            "}";
    }
}
