package com.example.clearsolutiontestapp.services;

import com.example.clearsolutiontestapp.domain.User;
import com.example.clearsolutiontestapp.repository.UserRepository;
import com.example.clearsolutiontestapp.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testCreate() {
        User user = new User();
        user.setBirthDate(LocalDate.of(2000, 1, 1));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.create(user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testUpdateSomeFields() {
        User user = new User();
        user.setBirthDate(LocalDate.of(2000, 1, 1));
        when(userRepository.findById(anyInt())).thenReturn(java.util.Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        Map<String, Object> fieldsToUpdate = new HashMap<>();
        userService.updateSomeFields(1, fieldsToUpdate);

        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testUpdateAllFields() {
        User user = new User();
        user.setBirthDate(LocalDate.of(2000, 1, 1));
        when(userRepository.findById(anyInt())).thenReturn(java.util.Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.updateAllFields(1, user);

        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testDelete() {
        User user = new User();
        when(userRepository.findById(anyInt())).thenReturn(java.util.Optional.of(user));

        userService.delete(1);

        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void testGetByBirthRange() {
        when(userRepository.findByBirthDateBetween(any(LocalDate.class), any(LocalDate.class))).thenReturn(Collections.emptyList());

        userService.getByBirthRange(LocalDate.now(), LocalDate.now());

        verify(userRepository, times(1)).findByBirthDateBetween(any(LocalDate.class), any(LocalDate.class));
    }
}
