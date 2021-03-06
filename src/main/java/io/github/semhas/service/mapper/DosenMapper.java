package io.github.semhas.service.mapper;

import io.github.semhas.domain.*;
import io.github.semhas.service.dto.DosenDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Dosen and its DTO DosenDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface DosenMapper extends EntityMapper <DosenDTO, Dosen> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    DosenDTO toDto(Dosen dosen); 

    @Mapping(source = "userId", target = "user")
    Dosen toEntity(DosenDTO dosenDTO); 
    default Dosen fromId(Long id) {
        if (id == null) {
            return null;
        }
        Dosen dosen = new Dosen();
        dosen.setId(id);
        return dosen;
    }
}
