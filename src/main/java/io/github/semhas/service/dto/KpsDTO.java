package io.github.semhas.service.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class KpsDTO implements Serializable {
    private MahasiswaDTO mahasiswa;
    private List<SeminarDTO> seminars = new ArrayList<>();

    public MahasiswaDTO getMahasiswa() {
        return mahasiswa;
    }

    public void setMahasiswa(MahasiswaDTO mahasiswa) {
        this.mahasiswa = mahasiswa;
    }

    public List<SeminarDTO> getSeminars() {
        return seminars;
    }

    public void setSeminars(List<SeminarDTO> seminars) {
        this.seminars = seminars;
    }
}
