package io.github.semhas.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Sesi.
 */
@Entity
@Table(name = "sesi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Sesi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "urutan")
    private Integer urutan;

    @Column(name = "jam_mulai")
    private ZonedDateTime jamMulai;

    @Column(name = "jam_selesai")
    private ZonedDateTime jamSelesai;

    @OneToMany(mappedBy = "sesi")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<JadwalSeminar> listJadwalSeminars = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUrutan() {
        return urutan;
    }

    public Sesi urutan(Integer urutan) {
        this.urutan = urutan;
        return this;
    }

    public void setUrutan(Integer urutan) {
        this.urutan = urutan;
    }

    public ZonedDateTime getJamMulai() {
        return jamMulai;
    }

    public Sesi jamMulai(ZonedDateTime jamMulai) {
        this.jamMulai = jamMulai;
        return this;
    }

    public void setJamMulai(ZonedDateTime jamMulai) {
        this.jamMulai = jamMulai;
    }

    public ZonedDateTime getJamSelesai() {
        return jamSelesai;
    }

    public Sesi jamSelesai(ZonedDateTime jamSelesai) {
        this.jamSelesai = jamSelesai;
        return this;
    }

    public void setJamSelesai(ZonedDateTime jamSelesai) {
        this.jamSelesai = jamSelesai;
    }

    public Set<JadwalSeminar> getListJadwalSeminars() {
        return listJadwalSeminars;
    }

    public Sesi listJadwalSeminars(Set<JadwalSeminar> jadwalSeminars) {
        this.listJadwalSeminars = jadwalSeminars;
        return this;
    }

    public Sesi addListJadwalSeminar(JadwalSeminar jadwalSeminar) {
        this.listJadwalSeminars.add(jadwalSeminar);
        jadwalSeminar.setSesi(this);
        return this;
    }

    public Sesi removeListJadwalSeminar(JadwalSeminar jadwalSeminar) {
        this.listJadwalSeminars.remove(jadwalSeminar);
        jadwalSeminar.setSesi(null);
        return this;
    }

    public void setListJadwalSeminars(Set<JadwalSeminar> jadwalSeminars) {
        this.listJadwalSeminars = jadwalSeminars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sesi sesi = (Sesi) o;
        if (sesi.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sesi.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Sesi{" +
            "id=" + getId() +
            ", urutan='" + getUrutan() + "'" +
            ", jamMulai='" + getJamMulai() + "'" +
            ", jamSelesai='" + getJamSelesai() + "'" +
            "}";
    }
}
