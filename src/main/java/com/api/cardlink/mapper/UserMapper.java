package com.api.cardlink.mapper;

import com.api.cardlink.Dto.UserDto.UserRequestDto;
import com.api.cardlink.Dto.UserDto.UserResponseDto;
import com.api.cardlink.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto toDto(User user);

    @Mapping(target = "createdAt", ignore = true) // Ignorer le champ importedDate lors de la conversion de DTO à Entity
    @Mapping(target = "lastUpdatedAt", ignore = true) // Ignorer le champ lastUpdated
    User toEntity(UserRequestDto userRequestDto);

}
