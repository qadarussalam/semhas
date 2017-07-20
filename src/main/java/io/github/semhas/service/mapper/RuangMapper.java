package io.github.semhas.service.mapper;

import io.github.semhas.domain.*;
import io.github.semhas.service.dto.RuangDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Ruang and its DTO RuangDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface RuangMapper extends EntityMapper <RuangDTO, Ruang> {

    @Mapping(source = "pic.id", target = "picId")
    @Mapping(source = "pic.login", target = "picLogin")
    RuangDTO toDto(Ruang ruang); 

    @Mapping(source = "picId", target = "pic")
    @Mapping(target = "listJadwalSeminars", ignore = true)
    Ruang toEntity(RuangDTO ruangDTO); 
    default Ruang fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ruang ruang = new Ruang();
        ruang.setId(id);
        return ruang;
    }
}
