package io.github.semhas.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Ruang.
 */
@Entity
@Table(name = "ruang")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ruang implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nama")
    private String nama;

    @Column(name = "kapasitas")
    private Integer kapasitas;

    @OneToOne
    @JoinColumn(unique = true)
    private User pic;

    @OneToMany(mappedBy = "ruang")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<JadwalSeminar> listJadwalSeminars = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public Ruang nama(String nama) {
        this.nama = nama;
        return this;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Integer getKapasitas() {
        return kapasitas;
    }

    public Ruang kapasitas(Integer kapasitas) {
        this.kapasitas = kapasitas;
        return this;
    }

    public void setKapasitas(Integer kapasitas) {
        this.kapasitas = kapasitas;
    }

    public User getPic() {
        return pic;
    }

    public Ruang pic(User user) {
        this.pic = user;
        return this;
    }

    public void setPic(User user) {
        this.pic = user;
    }

    public Set<JadwalSeminar> getListJadwalSeminars() {
        return listJadwalSeminars;
    }

    public Ruang listJadwalSeminars(Set<JadwalSeminar> jadwalSeminars) {
        this.listJadwalSeminars = jadwalSeminars;
        return this;
    }

    public Ruang addListJadwalSeminar(JadwalSeminar jadwalSeminar) {
        this.listJadwalSeminars.add(jadwalSeminar);
        jadwalSeminar.setRuang(this);
        return this;
    }

    public Ruang removeListJadwalSeminar(JadwalSeminar jadwalSeminar) {
        this.listJadwalSeminars.remove(jadwalSeminar);
        jadwalSeminar.setRuang(null);
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
        Ruang ruang = (Ruang) o;
        if (ruang.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ruang.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ruang{" +
            "id=" + getId() +
            ", nama='" + getNama() + "'" +
            ", kapasitas='" + getKapasitas() + "'" +
            "}";
    }
}
