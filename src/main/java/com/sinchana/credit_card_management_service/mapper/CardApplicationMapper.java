package com.sinchana.credit_card_management_service.mapper;

import com.sinchana.credit_card_management_service.dto.request.CardApplicationRequestDTO;
import com.sinchana.credit_card_management_service.dto.response.CardApplicationResponseDTO;
import com.sinchana.credit_card_management_service.entity.CardApplication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CardApplicationMapper {

    CardApplicationMapper INSTANCE = Mappers.getMapper(CardApplicationMapper.class);

    // Request DTO → Entity
    @Mapping(target = "id", ignore = true)              // auto-generated
    @Mapping(target = "applicationDate", ignore = true) // set in service
    @Mapping(target = "status", ignore = true)          // default PENDING
    @Mapping(target = "reviewedBy", ignore = true)
    @Mapping(target = "reviewDate", ignore = true)
    CardApplication toEntity(CardApplicationRequestDTO dto);

    // Entity → Response DTO
    CardApplicationResponseDTO toResponseDTO(CardApplication entity);
}
