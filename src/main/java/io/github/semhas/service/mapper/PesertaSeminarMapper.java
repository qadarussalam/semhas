package io.github.semhas.service.mapper;

import io.github.semhas.domain.*;
import io.github.semhas.service.dto.PesertaSeminarDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PesertaSeminar and its DTO PesertaSeminarDTO.
 */
@Mapper(componentModel = "spring", uses = {MahasiswaMapper.class, SeminarMapper.class, })
public interface PesertaSeminarMapper extends EntityMapper <PesertaSeminarDTO, PesertaSeminar> {

    @Mapping(source = "mahasiswa.id", target = "mahasiswaId")
    PesertaSeminarDTO toDto(PesertaSeminar pesertaSeminar); 

    @Mapping(source = "mahasiswaId", target = "mahasiswa")
    PesertaSeminar toEntity(PesertaSeminarDTO pesertaSeminarDTO); 
    default PesertaSeminar fromId(Long id) {
        if (id == null) {
            return null;
        }
        PesertaSeminar pesertaSeminar = new PesertaSeminar();
        pesertaSeminar.setId(id);
        return pesertaSeminar;
    }
}
