package io.github.semhas.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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

    private Long userId;

    private String userLogin;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
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
