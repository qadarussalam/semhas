package io.github.semhas.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Jurusan.
 */
@Entity
@Table(name = "jurusan")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Jurusan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5)
    @Column(name = "nama", nullable = false)
    private String nama;

    @OneToOne
    @JoinColumn(unique = true)
    private User pic;

    @OneToMany(mappedBy = "jurusan")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Mahasiswa> listMahasiswas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public Jurusan nama(String nama) {
        this.nama = nama;
        return this;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public User getPic() {
        return pic;
    }

    public Jurusan pic(User user) {
        this.pic = user;
        return this;
    }

    public void setPic(User user) {
        this.pic = user;
    }

    public Set<Mahasiswa> getListMahasiswas() {
        return listMahasiswas;
    }

    public Jurusan listMahasiswas(Set<Mahasiswa> mahasiswas) {
        this.listMahasiswas = mahasiswas;
        return this;
    }

    public Jurusan addListMahasiswa(Mahasiswa mahasiswa) {
        this.listMahasiswas.add(mahasiswa);
        mahasiswa.setJurusan(this);
        return this;
    }

    public Jurusan removeListMahasiswa(Mahasiswa mahasiswa) {
        this.listMahasiswas.remove(mahasiswa);
        mahasiswa.setJurusan(null);
        return this;
    }

    public void setListMahasiswas(Set<Mahasiswa> mahasiswas) {
        this.listMahasiswas = mahasiswas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Jurusan jurusan = (Jurusan) o;
        if (jurusan.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jurusan.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Jurusan{" +
            "id=" + getId() +
            ", nama='" + getNama() + "'" +
            "}";
    }
}
