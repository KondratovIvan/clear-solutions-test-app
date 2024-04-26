package com.example.clearsolutiontestapp.controllers;

import com.example.clearsolutiontestapp.domain.User;
import com.example.clearsolutiontestapp.dto.UserDto;
import com.example.clearsolutiontestapp.service.UserService;
import com.example.clearsolutiontestapp.util.config.mapper.UserMapper;
import com.example.clearsolutiontestapp.web.UserController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Collections;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = UserController.class)
@DisplayName("User Controller Tests")
public class UserControllerTest {

    @Autowired
    ObjectMapper mapper;

    @MockBean
    UserService userService;

    @MockBean
    UserMapper userMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("POST /api/v1/users")
    public void saveUserTest() throws Exception {
        UserDto userDto = createUserDto();
        User user = createUser();

        when(userMapper.fromUserDto(any(UserDto.class))).thenReturn(user);
        when(userMapper.toUserDto(any(User.class))).thenReturn(userDto);
        when(userService.create(any(User.class))).thenReturn(user);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userDto));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated());


        verify(userService).create(any());
    }

    @Test
    @DisplayName("PUT /api/v1/users/{id}/fields")
    public void updateSomeUsersFieldsTest() throws Exception {
        UserDto userDto = createUserDto();
        User user = createUser();

        when(userMapper.fromUserDto(any(UserDto.class))).thenReturn(user);
        when(userMapper.toUserDto(any(User.class))).thenReturn(userDto);
        when(userService.updateSomeFields(anyInt(), anyMap())).thenReturn(user);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/api/v1/users/1/fields")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userDto));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());

        verify(userService).updateSomeFields(anyInt(), anyMap());
    }

    @Test
    @DisplayName("PUT /api/v1/users/{id}")
    public void updateAllUsersFieldsTest() throws Exception {
        UserDto userDto = createUserDto();
        User user = createUser();

        when(userMapper.fromUserDto(any(UserDto.class))).thenReturn(user);
        when(userMapper.toUserDto(any(User.class))).thenReturn(userDto);
        when(userService.updateAllFields(anyInt(), any(User.class))).thenReturn(user);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userDto));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());

        verify(userService).updateAllFields(anyInt(), any(User.class));
    }

    @Test
    @DisplayName("DELETE /api/v1/users/{id}")
    public void removeUserTest() throws Exception {
        doNothing().when(userService).delete(anyInt());

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .delete("/api/v1/users/1");

        mockMvc.perform(mockRequest)
                .andExpect(status().isNoContent());

        verify(userService).delete(anyInt());
    }

    @Test
    @DisplayName("GET /api/v1/users/birthdate")
    public void getUsersByBirthRangeTest() throws Exception {
        UserDto userDto = createUserDto();
        User user = createUser();

        when(userMapper.fromUserDto(any(UserDto.class))).thenReturn(user);
        when(userMapper.toUserDto(any(User.class))).thenReturn(userDto);
        when(userService.getByBirthRange(any(LocalDate.class), any(LocalDate.class))).thenReturn(Collections.singletonList(user));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/api/v1/users/birthdate")
                .param("startDate", "2000-01-01")
                .param("endDate", "2000-01-02");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());

        verify(userService).getByBirthRange(any(LocalDate.class), any(LocalDate.class));
    }

    private UserDto createUserDto() {
        UserDto userDto = new UserDto();
        userDto.setEmail("mail@mail.com");
        userDto.setFirstName("Mike");
        userDto.setLastName("Smith");
        userDto.setBirthDate(LocalDate.of(2000, 1, 1));
        userDto.setAddress("123 Street");
        userDto.setPhoneNumber("1234567890");
        return userDto;
    }

    private User createUser() {
        User user = new User();
        user.setId(1);
        user.setEmail("mail@mail.com");
        user.setFirstName("Mike");
        user.setLastName("Smith");
        user.setBirthDate(LocalDate.of(2000, 1, 1));
        user.setAddress("123 Street");
        user.setPhoneNumber("1234567890");
        return user;
    }

}