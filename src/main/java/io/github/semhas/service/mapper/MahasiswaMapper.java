package io.github.semhas.service.mapper;

import io.github.semhas.domain.*;
import io.github.semhas.service.dto.MahasiswaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Mahasiswa and its DTO MahasiswaDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, JurusanMapper.class, })
public interface MahasiswaMapper extends EntityMapper <MahasiswaDTO, Mahasiswa> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")

    @Mapping(source = "jurusan.id", target = "jurusanId")
    @Mapping(source = "jurusan.nama", target = "jurusanNama")
    MahasiswaDTO toDto(Mahasiswa mahasiswa); 

    @Mapping(source = "userId", target = "user")

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
