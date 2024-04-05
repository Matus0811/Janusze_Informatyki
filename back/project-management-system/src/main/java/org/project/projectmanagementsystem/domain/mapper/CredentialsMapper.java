package org.project.projectmanagementsystem.domain.mapper;

import org.project.projectmanagementsystem.api.dto.CredentialsDTO;
import org.project.projectmanagementsystem.domain.Credentials;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CredentialsMapper {
    CredentialsMapper INSTANCE = Mappers.getMapper(CredentialsMapper.class);

    CredentialsDTO mapFromDomainToDto(Credentials credentials);

    Credentials mapFromDtoToDomain(CredentialsDTO credentialsDTO);
}
