package io.github.semhas.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A JadwalSeminar.
 */
@Entity
@Table(name = "jadwal_seminar")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class JadwalSeminar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "tanggal")
    private ZonedDateTime tanggal;

    @Column(name = "tersedia", columnDefinition = "TINYINT", length = 1)
    private Boolean tersedia;

    @ManyToOne
    private Sesi sesi;

    @ManyToOne
    private Ruang ruang;

    @OneToOne(mappedBy = "jadwalSeminar")
    @JsonIgnore
    private Seminar seminar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getTanggal() {
        return tanggal;
    }

    public JadwalSeminar tanggal(ZonedDateTime tanggal) {
        this.tanggal = tanggal;
        return this;
    }

    public void setTanggal(ZonedDateTime tanggal) {
        this.tanggal = tanggal;
    }

    public Boolean isTersedia() {
        return tersedia;
    }

    public JadwalSeminar tersedia(Boolean tersedia) {
        this.tersedia = tersedia;
        return this;
    }

    public void setTersedia(Boolean tersedia) {
        this.tersedia = tersedia;
    }

    public Sesi getSesi() {
        return sesi;
    }

    public JadwalSeminar sesi(Sesi sesi) {
        this.sesi = sesi;
        return this;
    }

    public void setSesi(Sesi sesi) {
        this.sesi = sesi;
    }

    public Ruang getRuang() {
        return ruang;
    }

    public JadwalSeminar ruang(Ruang ruang) {
        this.ruang = ruang;
        return this;
    }

    public void setRuang(Ruang ruang) {
        this.ruang = ruang;
    }

    public Seminar getSeminar() {
        return seminar;
    }

    public JadwalSeminar seminar(Seminar seminar) {
        this.seminar = seminar;
        return this;
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
        JadwalSeminar jadwalSeminar = (JadwalSeminar) o;
        if (jadwalSeminar.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jadwalSeminar.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JadwalSeminar{" +
            "id=" + getId() +
            ", tanggal='" + getTanggal() + "'" +
            ", tersedia='" + isTersedia() + "'" +
            "}";
    }
}
