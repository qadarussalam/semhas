package io.github.semhas.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.github.semhas.domain.enumeration.AbsensiSeminar;

/**
 * A PesertaSeminar.
 */
@Entity
@Table(name = "peserta_seminar")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PesertaSeminar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "absensi")
    private AbsensiSeminar absensi;

    @ManyToOne
    private Mahasiswa mahasiswa;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "peserta_seminar_list_seminar",
               joinColumns = @JoinColumn(name="peserta_seminars_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="list_seminars_id", referencedColumnName="id"))
    private Set<Seminar> listSeminars = new HashSet<>();

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

    public Set<Seminar> getListSeminars() {
        return listSeminars;
    }

    public PesertaSeminar listSeminars(Set<Seminar> seminars) {
        this.listSeminars = seminars;
        return this;
    }

    public PesertaSeminar addListSeminar(Seminar seminar) {
        this.listSeminars.add(seminar);
        seminar.getListPesertaSeminars().add(this);
        return this;
    }

    public PesertaSeminar removeListSeminar(Seminar seminar) {
        this.listSeminars.remove(seminar);
        seminar.getListPesertaSeminars().remove(this);
        return this;
    }

    public void setListSeminars(Set<Seminar> seminars) {
        this.listSeminars = seminars;
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
            "}";
    }
}
