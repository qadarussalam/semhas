package io.github.semhas.service.mapper;

import io.github.semhas.domain.*;
import io.github.semhas.service.dto.SesiDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Sesi and its DTO SesiDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SesiMapper extends EntityMapper <SesiDTO, Sesi> {
    
    @Mapping(target = "listJadwalSeminars", ignore = true)
    Sesi toEntity(SesiDTO sesiDTO); 
    default Sesi fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sesi sesi = new Sesi();
        sesi.setId(id);
        return sesi;
    }
}
