package io.github.semhas.web.rest.vm;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Set;

public class ManagedMahasiswaUserVM extends ManagedUserVM {

    @NotNull
    private String nim;

    @NotNull
    private Integer semester;

    @NotNull
    private String nomorTelepon;

    @NotNull
    private Long jurusanId;

    public ManagedMahasiswaUserVM() {
    }

    public ManagedMahasiswaUserVM(Long id, String login, String password, String firstName, String lastName, String email, boolean activated, String imageUrl, String langKey, String createdBy, Instant createdDate, String lastModifiedBy, Instant lastModifiedDate, Set<String> authorities, String nim, Integer semester, String nomorTelepon, Long jurusanId) {
        super(id, login, password, firstName, lastName, email, activated, imageUrl, langKey, createdBy, createdDate, lastModifiedBy, lastModifiedDate, authorities);
        this.nim = nim;
        this.semester = semester;
        this.nomorTelepon = nomorTelepon;
        this.jurusanId = jurusanId;
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

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }

    public Long getJurusanId() {
        return jurusanId;
    }

    public void setJurusanId(Long jurusanId) {
        this.jurusanId = jurusanId;
    }
}
