package io.github.semhas.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Mahasiswa.
 */
@Entity
@Table(name = "mahasiswa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Mahasiswa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Column(name = "nim", nullable = false)
    private String nim;

    @NotNull
    @Column(name = "semester", nullable = false)
    private Integer semester;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "nomor_telepon")
    private String nomorTelepon;

    @Lob
    @Column(name = "foto", nullable = true)
    private byte[] foto;

    @Column(name = "foto_content_type", nullable = true)
    private String fotoContentType;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @ManyToOne
    private Jurusan jurusan;

    @OneToOne(mappedBy = "mahasiswa")
    @JsonIgnore
    private Seminar seminar;

    @OneToMany(mappedBy = "mahasiswa")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PesertaSeminar> listPesertaSeminars = new HashSet<>();

    public Mahasiswa(Long id) {
        this.id = id;
    }

    public Mahasiswa() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public Mahasiswa nama(String nama) {
        this.nama = nama;
        return this;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNim() {
        return nim;
    }

    public Mahasiswa nim(String nim) {
        this.nim = nim;
        return this;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public Integer getSemester() {
        return semester;
    }

    public Mahasiswa semester(Integer semester) {
        this.semester = semester;
        return this;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public String getEmail() {
        return email;
    }

    public Mahasiswa email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public Mahasiswa nomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
        return this;
    }

    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }

    public byte[] getFoto() {
        return foto;
    }

    public Mahasiswa foto(byte[] foto) {
        this.foto = foto;
        return this;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }

    public Mahasiswa fotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
        return this;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public User getUser() {
        return user;
    }

    public Mahasiswa user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Jurusan getJurusan() {
        return jurusan;
    }

    public Mahasiswa jurusan(Jurusan jurusan) {
        this.jurusan = jurusan;
        return this;
    }

    public void setJurusan(Jurusan jurusan) {
        this.jurusan = jurusan;
    }

    public Seminar getSeminar() {
        return seminar;
    }

    public Mahasiswa seminar(Seminar seminar) {
        this.seminar = seminar;
        return this;
    }

    public void setSeminar(Seminar seminar) {
        this.seminar = seminar;
    }

    public Set<PesertaSeminar> getListPesertaSeminars() {
        return listPesertaSeminars;
    }

    public Mahasiswa listPesertaSeminars(Set<PesertaSeminar> pesertaSeminars) {
        this.listPesertaSeminars = pesertaSeminars;
        return this;
    }

    public Mahasiswa addListPesertaSeminar(PesertaSeminar pesertaSeminar) {
        this.listPesertaSeminars.add(pesertaSeminar);
        pesertaSeminar.setMahasiswa(this);
        return this;
    }

    public Mahasiswa removeListPesertaSeminar(PesertaSeminar pesertaSeminar) {
        this.listPesertaSeminars.remove(pesertaSeminar);
        pesertaSeminar.setMahasiswa(null);
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
        Mahasiswa mahasiswa = (Mahasiswa) o;
        if (mahasiswa.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mahasiswa.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Mahasiswa{" +
            "id=" + getId() +
            ", nama='" + getNama() + "'" +
            ", nim='" + getNim() + "'" +
            ", semester='" + getSemester() + "'" +
            ", email='" + getEmail() + "'" +
            ", nomorTelepon='" + getNomorTelepon() + "'" +
            ", foto='" + getFoto() + "'" +
            ", fotoContentType='" + fotoContentType + "'" +
            "}";
    }
}
