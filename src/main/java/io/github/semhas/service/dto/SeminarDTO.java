package io.github.semhas.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;
import io.github.semhas.domain.enumeration.StatusSeminar;

/**
 * A DTO for the Seminar entity.
 */
public class SeminarDTO implements Serializable {

    private Long id;

    @NotNull
    private String judul;

    @NotNull
    @Lob
    private String abstrak;

    @NotNull
    @Lob
    private byte[] fileAccSeminar;
    private String fileAccSeminarContentType;

    private String ruangan;

    private ZonedDateTime jamMulai;

    private ZonedDateTime jamSelesai;

    private StatusSeminar status;

    private Long mahasiswaId;

    private String mahasiswaNama;

    private Long jadwalSeminarId;

    private String jadwalSeminarTanggal;

    private Long dosenPertamaId;

    private String dosenPertamaNama;

    private Long dosenKeduaId;

    private String dosenKeduaNama;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getAbstrak() {
        return abstrak;
    }

    public void setAbstrak(String abstrak) {
        this.abstrak = abstrak;
    }

    public byte[] getFileAccSeminar() {
        return fileAccSeminar;
    }

    public void setFileAccSeminar(byte[] fileAccSeminar) {
        this.fileAccSeminar = fileAccSeminar;
    }

    public String getFileAccSeminarContentType() {
        return fileAccSeminarContentType;
    }

    public void setFileAccSeminarContentType(String fileAccSeminarContentType) {
        this.fileAccSeminarContentType = fileAccSeminarContentType;
    }

    public String getRuangan() {
        return ruangan;
    }

    public void setRuangan(String ruangan) {
        this.ruangan = ruangan;
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

    public StatusSeminar getStatus() {
        return status;
    }

    public void setStatus(StatusSeminar status) {
        this.status = status;
    }

    public Long getMahasiswaId() {
        return mahasiswaId;
    }

    public void setMahasiswaId(Long mahasiswaId) {
        this.mahasiswaId = mahasiswaId;
    }

    public String getMahasiswaNama() {
        return mahasiswaNama;
    }

    public void setMahasiswaNama(String mahasiswaNama) {
        this.mahasiswaNama = mahasiswaNama;
    }

    public Long getJadwalSeminarId() {
        return jadwalSeminarId;
    }

    public void setJadwalSeminarId(Long jadwalSeminarId) {
        this.jadwalSeminarId = jadwalSeminarId;
    }

    public String getJadwalSeminarTanggal() {
        return jadwalSeminarTanggal;
    }

    public void setJadwalSeminarTanggal(String jadwalSeminarTanggal) {
        this.jadwalSeminarTanggal = jadwalSeminarTanggal;
    }

    public Long getDosenPertamaId() {
        return dosenPertamaId;
    }

    public void setDosenPertamaId(Long dosenId) {
        this.dosenPertamaId = dosenId;
    }

    public String getDosenPertamaNama() {
        return dosenPertamaNama;
    }

    public void setDosenPertamaNama(String dosenNama) {
        this.dosenPertamaNama = dosenNama;
    }

    public Long getDosenKeduaId() {
        return dosenKeduaId;
    }

    public void setDosenKeduaId(Long dosenId) {
        this.dosenKeduaId = dosenId;
    }

    public String getDosenKeduaNama() {
        return dosenKeduaNama;
    }

    public void setDosenKeduaNama(String dosenNama) {
        this.dosenKeduaNama = dosenNama;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SeminarDTO seminarDTO = (SeminarDTO) o;
        if(seminarDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seminarDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SeminarDTO{" +
            "id=" + getId() +
            ", judul='" + getJudul() + "'" +
            ", abstrak='" + getAbstrak() + "'" +
            ", fileAccSeminar='" + getFileAccSeminar() + "'" +
            ", ruangan='" + getRuangan() + "'" +
            ", jamMulai='" + getJamMulai() + "'" +
            ", jamSelesai='" + getJamSelesai() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
