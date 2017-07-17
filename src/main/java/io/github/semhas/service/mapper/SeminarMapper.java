package io.github.semhas.service.mapper;

import io.github.semhas.domain.*;
import io.github.semhas.service.dto.SeminarDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Seminar and its DTO SeminarDTO.
 */
@Mapper(componentModel = "spring", uses = {MahasiswaMapper.class, JadwalSeminarMapper.class, DosenMapper.class, })
public interface SeminarMapper extends EntityMapper <SeminarDTO, Seminar> {

    @Mapping(source = "mahasiswa.id", target = "mahasiswaId")

    @Mapping(source = "jadwalSeminar.id", target = "jadwalSeminarId")

    @Mapping(source = "dosenPertama.id", target = "dosenPertamaId")

    @Mapping(source = "dosenKedua.id", target = "dosenKeduaId")
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
