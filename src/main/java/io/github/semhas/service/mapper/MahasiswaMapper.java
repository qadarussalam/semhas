package io.github.semhas.service.mapper;

import io.github.semhas.domain.*;
import io.github.semhas.service.dto.MahasiswaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Mahasiswa and its DTO MahasiswaDTO.
 */
@Mapper(componentModel = "spring", uses = {JurusanMapper.class, })
public interface MahasiswaMapper extends EntityMapper <MahasiswaDTO, Mahasiswa> {

    @Mapping(source = "jurusan.id", target = "jurusanId")
    MahasiswaDTO toDto(Mahasiswa mahasiswa); 

    @Mapping(source = "jurusanId", target = "jurusan")
    @Mapping(target = "seminar", ignore = true)
    @Mapping(target = "listPesertaSeminars", ignore = true)
    Mahasiswa toEntity(MahasiswaDTO mahasiswaDTO); 
    default Mahasiswa fromId(Long id) {
        if (id == null) {
            return null;
        }
        Mahasiswa mahasiswa = new Mahasiswa();
        mahasiswa.setId(id);
        return mahasiswa;
    }
}
