package io.github.semhas.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.semhas.domain.enumeration.StatusSeminar;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Seminar.
 */
@Entity
@Table(name = "seminar")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Seminar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "judul", nullable = false)
    private String judul;

    @NotNull
    @Lob
    @Column(name = "abstrak", nullable = false)
    private String abstrak;

    @NotNull
    @Lob
    @Column(name = "file_acc_seminar", nullable = false)
    private byte[] fileAccSeminar;

    @Column(name = "file_acc_seminar_content_type", nullable = false)
    private String fileAccSeminarContentType;

    @Column(name = "ruangan")
    private String ruangan;

    @Column(name = "jam_mulai")
    private ZonedDateTime jamMulai;

    @Column(name = "jam_selesai")
    private ZonedDateTime jamSelesai;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusSeminar status;

    @OneToOne
    @JoinColumn(unique = true)
    private Mahasiswa mahasiswa;

    @OneToOne
    @JoinColumn(unique = true)
    private JadwalSeminar jadwalSeminar;

    @ManyToOne
    private Dosen dosenPertama;

    @ManyToOne
    private Dosen dosenKedua;

    @OneToMany(mappedBy = "seminar")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PesertaSeminar> listPesertaSeminars = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public Seminar judul(String judul) {
        this.judul = judul;
        return this;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getAbstrak() {
        return abstrak;
    }

    public Seminar abstrak(String abstrak) {
        this.abstrak = abstrak;
        return this;
    }

    public void setAbstrak(String abstrak) {
        this.abstrak = abstrak;
    }

    public byte[] getFileAccSeminar() {
        return fileAccSeminar;
    }

    public Seminar fileAccSeminar(byte[] fileAccSeminar) {
        this.fileAccSeminar = fileAccSeminar;
        return this;
    }

    public void setFileAccSeminar(byte[] fileAccSeminar) {
        this.fileAccSeminar = fileAccSeminar;
    }

    public String getFileAccSeminarContentType() {
        return fileAccSeminarContentType;
    }

    public Seminar fileAccSeminarContentType(String fileAccSeminarContentType) {
        this.fileAccSeminarContentType = fileAccSeminarContentType;
        return this;
    }

    public void setFileAccSeminarContentType(String fileAccSeminarContentType) {
        this.fileAccSeminarContentType = fileAccSeminarContentType;
    }

    public String getRuangan() {
        return ruangan;
    }

    public Seminar ruangan(String ruangan) {
        this.ruangan = ruangan;
        return this;
    }

    public void setRuangan(String ruangan) {
        this.ruangan = ruangan;
    }

    public ZonedDateTime getJamMulai() {
        return jamMulai;
    }

    public Seminar jamMulai(ZonedDateTime jamMulai) {
        this.jamMulai = jamMulai;
        return this;
    }

    public void setJamMulai(ZonedDateTime jamMulai) {
        this.jamMulai = jamMulai;
    }

    public ZonedDateTime getJamSelesai() {
        return jamSelesai;
    }

    public Seminar jamSelesai(ZonedDateTime jamSelesai) {
        this.jamSelesai = jamSelesai;
        return this;
    }

    public void setJamSelesai(ZonedDateTime jamSelesai) {
        this.jamSelesai = jamSelesai;
    }

    public StatusSeminar getStatus() {
        return status;
    }

    public Seminar status(StatusSeminar status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusSeminar status) {
        this.status = status;
    }

    public Mahasiswa getMahasiswa() {
        return mahasiswa;
    }

    public Seminar mahasiswa(Mahasiswa mahasiswa) {
        this.mahasiswa = mahasiswa;
        return this;
    }

    public void setMahasiswa(Mahasiswa mahasiswa) {
        this.mahasiswa = mahasiswa;
    }

    public JadwalSeminar getJadwalSeminar() {
        return jadwalSeminar;
    }

    public Seminar jadwalSeminar(JadwalSeminar jadwalSeminar) {
        this.jadwalSeminar = jadwalSeminar;
        return this;
    }

    public void setJadwalSeminar(JadwalSeminar jadwalSeminar) {
        this.jadwalSeminar = jadwalSeminar;
    }

    public Dosen getDosenPertama() {
        return dosenPertama;
    }

    public Seminar dosenPertama(Dosen dosen) {
        this.dosenPertama = dosen;
        return this;
    }

    public void setDosenPertama(Dosen dosen) {
        this.dosenPertama = dosen;
    }

    public Dosen getDosenKedua() {
        return dosenKedua;
    }

    public Seminar dosenKedua(Dosen dosen) {
        this.dosenKedua = dosen;
        return this;
    }

    public void setDosenKedua(Dosen dosen) {
        this.dosenKedua = dosen;
    }

    public Set<PesertaSeminar> getListPesertaSeminars() {
        return listPesertaSeminars;
    }

    public Seminar listPesertaSeminars(Set<PesertaSeminar> pesertaSeminars) {
        this.listPesertaSeminars = pesertaSeminars;
        return this;
    }

    public void setListPesertaSeminars(Set<PesertaSeminar> pesertaSeminars) {
        this.listPesertaSeminars = pesertaSeminars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Seminar seminar = (Seminar) o;
        if (seminar.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seminar.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Seminar{" +
            "id=" + getId() +
            ", judul='" + getJudul() + "'" +
            ", abstrak='" + getAbstrak() + "'" +
            ", fileAccSeminar='" + getFileAccSeminar() + "'" +
            ", fileAccSeminarContentType='" + fileAccSeminarContentType + "'" +
            ", ruangan='" + getRuangan() + "'" +
            ", jamMulai='" + getJamMulai() + "'" +
            ", jamSelesai='" + getJamSelesai() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
