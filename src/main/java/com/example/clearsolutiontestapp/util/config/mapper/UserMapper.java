package com.example.clearsolutiontestapp.util.config.mapper;

import com.example.clearsolutiontestapp.domain.User;
import com.example.clearsolutiontestapp.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User fromUserDto(UserDto userDto);

    UserDto toUserDto(User user);
}
