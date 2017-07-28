package io.github.semhas.domain;

import io.github.semhas.domain.enumeration.AbsensiSeminar;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PesertaSeminar.
 */
@Entity
@Table(name = "peserta_seminar")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PesertaSeminar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "absensi")
    private AbsensiSeminar absensi;

    @ManyToOne
    private Mahasiswa mahasiswa;

    @ManyToOne
    @JoinColumn(name = "seminar_id")
    private Seminar seminar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AbsensiSeminar getAbsensi() {
        return absensi;
    }

    public PesertaSeminar absensi(AbsensiSeminar absensi) {
        this.absensi = absensi;
        return this;
    }

    public void setAbsensi(AbsensiSeminar absensi) {
        this.absensi = absensi;
    }

    public Mahasiswa getMahasiswa() {
        return mahasiswa;
    }

    public PesertaSeminar mahasiswa(Mahasiswa mahasiswa) {
        this.mahasiswa = mahasiswa;
        return this;
    }

    public void setMahasiswa(Mahasiswa mahasiswa) {
        this.mahasiswa = mahasiswa;
    }

    public PesertaSeminar seminar(Seminar seminar) {
        this.seminar = seminar;
        return  this;
    }

    public Seminar getSeminar() {
        return seminar;
    }

    public void setSeminar(Seminar seminar) {
        this.seminar = seminar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PesertaSeminar pesertaSeminar = (PesertaSeminar) o;
        if (pesertaSeminar.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pesertaSeminar.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PesertaSeminar{" +
            "id=" + getId() +
            ", absensi='" + getAbsensi() + "'" +
            ", seminar='" + getSeminar() + "'" +
            "}";
    }
}
