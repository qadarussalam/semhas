package io.github.semhas.service.mapper;

import io.github.semhas.domain.Seminar;
import io.github.semhas.service.dto.SeminarDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Seminar and its DTO SeminarDTO.
 */
@Mapper(componentModel = "spring", uses = {MahasiswaMapper.class, JadwalSeminarMapper.class, DosenMapper.class, })
public interface SeminarMapper extends EntityMapper <SeminarDTO, Seminar> {

    @Mapping(source = "mahasiswa.id", target = "mahasiswaId")
    @Mapping(source = "mahasiswa.nama", target = "mahasiswaNama")
    @Mapping(source = "mahasiswa.nim", target = "mahasiswaNim")

    @Mapping(source = "jadwalSeminar.id", target = "jadwalSeminarId")
    @Mapping(source = "jadwalSeminar.tanggal", target = "jadwalSeminarTanggal")

    @Mapping(source = "dosenPertama.id", target = "dosenPertamaId")
    @Mapping(source = "dosenPertama.nama", target = "dosenPertamaNama")

    @Mapping(source = "dosenKedua.id", target = "dosenKeduaId")
    @Mapping(source = "dosenKedua.nama", target = "dosenKeduaNama")
    SeminarDTO toDto(Seminar seminar);

    @Mapping(source = "mahasiswaId", target = "mahasiswa")

    @Mapping(source = "jadwalSeminarId", target = "jadwalSeminar")

    @Mapping(source = "dosenPertamaId", target = "dosenPertama")

    @Mapping(source = "dosenKeduaId", target = "dosenKedua")
    @Mapping(target = "listPesertaSeminars", ignore = true)
    Seminar toEntity(SeminarDTO seminarDTO);
    default Seminar fromId(Long id) {
        if (id == null) {
            return null;
        }
        Seminar seminar = new Seminar();
        seminar.setId(id);
        return seminar;
    }
}
