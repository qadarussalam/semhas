package io.github.semhas.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Mahasiswa entity.
 */
public class MahasiswaDTO implements Serializable {

    private Long id;

    @NotNull
    private String nama;

    @NotNull
    private String nim;

    @NotNull
    private Integer semester;

    @NotNull
    private String email;

    private String nomorTelepon;

    @NotNull
    @Lob
    private byte[] foto;
    private String fotoContentType;

    private Long userId;

    private String userLogin;

    private Long jurusanId;

    private String jurusanNama;

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

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
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

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
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

    public Long getJurusanId() {
        return jurusanId;
    }

    public void setJurusanId(Long jurusanId) {
        this.jurusanId = jurusanId;
    }

    public String getJurusanNama() {
        return jurusanNama;
    }

    public void setJurusanNama(String jurusanNama) {
        this.jurusanNama = jurusanNama;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MahasiswaDTO mahasiswaDTO = (MahasiswaDTO) o;
        if(mahasiswaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mahasiswaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MahasiswaDTO{" +
            "id=" + getId() +
            ", nama='" + getNama() + "'" +
            ", nim='" + getNim() + "'" +
            ", semester='" + getSemester() + "'" +
            ", email='" + getEmail() + "'" +
            ", nomorTelepon='" + getNomorTelepon() + "'" +
            ", foto='" + getFoto() + "'" +
            "}";
    }
}
