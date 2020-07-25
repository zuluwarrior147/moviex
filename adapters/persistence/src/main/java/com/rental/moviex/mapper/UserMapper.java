package com.rental.moviex.mapper;

import com.rental.moviex.domain.User;
import com.rental.moviex.entity.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapConfig.class)
public interface UserMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastModified", ignore = true)
    UserEntity map(User user);

    @InheritInverseConfiguration
    User map(UserEntity userEntity);
}
