package com.sinchana.credit_card_management_service.mapper;

import com.sinchana.credit_card_management_service.dto.request.TransactionRequestDTO;
import com.sinchana.credit_card_management_service.dto.response.TransactionResponseDTO;
import com.sinchana.credit_card_management_service.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    // Request DTO → Entity
    @Mapping(target = "id", ignore = true)                   // auto-generated
    @Mapping(target = "transactionDate", ignore = true)      // set in service
    @Mapping(target = "paymentStatus", ignore = true)        // default set in service
    Transaction toEntity(TransactionRequestDTO dto);

    // Entity → Response DTO
    TransactionResponseDTO toResponseDTO(Transaction entity);
}
