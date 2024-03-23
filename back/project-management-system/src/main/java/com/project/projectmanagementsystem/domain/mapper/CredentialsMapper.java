package com.project.projectmanagementsystem.domain.mapper;

import com.project.projectmanagementsystem.controller.dto.CredentialsDTO;
import com.project.projectmanagementsystem.domain.Credentials;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CredentialsMapper {
    CredentialsMapper INSTANCE = Mappers.getMapper(CredentialsMapper.class);

    CredentialsDTO mapFromDomain(Credentials credentials);

    Credentials mapFromDto(CredentialsDTO credentialsDTO);
}
