package io.github.semhas.service.mapper;

import io.github.semhas.domain.*;
import io.github.semhas.service.dto.JurusanDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Jurusan and its DTO JurusanDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface JurusanMapper extends EntityMapper <JurusanDTO, Jurusan> {

    @Mapping(source = "pic.id", target = "picId")
    @Mapping(source = "pic.login", target = "picLogin")
    JurusanDTO toDto(Jurusan jurusan); 

    @Mapping(source = "picId", target = "pic")
    @Mapping(target = "listMahasiswas", ignore = true)
    Jurusan toEntity(JurusanDTO jurusanDTO); 
    default Jurusan fromId(Long id) {
        if (id == null) {
            return null;
        }
        Jurusan jurusan = new Jurusan();
        jurusan.setId(id);
        return jurusan;
    }
}
