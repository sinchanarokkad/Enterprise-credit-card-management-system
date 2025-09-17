package com.sinchana.credit_card_management_service.mapper;

import com.sinchana.credit_card_management_service.dto.request.UserRequestDTO;
import com.sinchana.credit_card_management_service.dto.response.UserResponseDTO;
import com.sinchana.credit_card_management_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // Request DTO → Entity
    @Mapping(target = "id", ignore = true)  // ID is auto-generated
    User toEntity(UserRequestDTO dto);

    // Entity → Response DTO
    UserResponseDTO toResponseDTO(User user);
}
