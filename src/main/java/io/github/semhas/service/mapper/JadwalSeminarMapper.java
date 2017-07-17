package io.github.semhas.service.mapper;

import io.github.semhas.domain.*;
import io.github.semhas.service.dto.JadwalSeminarDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity JadwalSeminar and its DTO JadwalSeminarDTO.
 */
@Mapper(componentModel = "spring", uses = {SesiMapper.class, RuangMapper.class, })
public interface JadwalSeminarMapper extends EntityMapper <JadwalSeminarDTO, JadwalSeminar> {

    @Mapping(source = "sesi.id", target = "sesiId")

    @Mapping(source = "ruang.id", target = "ruangId")
    JadwalSeminarDTO toDto(JadwalSeminar jadwalSeminar); 

    @Mapping(source = "sesiId", target = "sesi")

    @Mapping(source = "ruangId", target = "ruang")
    @Mapping(target = "seminar", ignore = true)
    JadwalSeminar toEntity(JadwalSeminarDTO jadwalSeminarDTO); 
    default JadwalSeminar fromId(Long id) {
        if (id == null) {
            return null;
        }
        JadwalSeminar jadwalSeminar = new JadwalSeminar();
        jadwalSeminar.setId(id);
        return jadwalSeminar;
    }
}
