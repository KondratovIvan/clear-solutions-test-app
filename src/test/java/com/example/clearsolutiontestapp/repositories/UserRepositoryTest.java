package com.example.clearsolutiontestapp.repositories;

import com.example.clearsolutiontestapp.domain.User;
import com.example.clearsolutiontestapp.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.assertj.core.api.Assertions;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("User Repository Tests")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    @DisplayName("Save user test")
    public void saveUserTest() {

        var user = User.builder()
                .email("test@gmail.com")
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(1990, 1, 1))
                .address("123 Main St")
                .phoneNumber("1234567890")
                .build();

        userRepository.save(user);

        Assertions.assertThat(user.getId()).isGreaterThan(0);
        Assertions.assertThat(user.getEmail()).isEqualTo("test@gmail.com");
    }

    @Test
    @Order(2)
    @DisplayName("Get user by id test")
    public void getUserTest() {

        var user = userRepository.findById(1).orElseThrow();

        Assertions.assertThat(user.getId()).isEqualTo(1);
        Assertions.assertThat(user.getEmail()).isEqualTo("test@gmail.com");
    }

    @Test
    @Order(3)
    @Rollback(value = false)
    @DisplayName("Update user test")
    public void updateUserTest() {

        var user = userRepository.findById(1).orElseThrow();

        user.setEmail("updated@example.com");
        var userUpdated = userRepository.save(user);

        Assertions.assertThat(userUpdated.getEmail()).isEqualTo("updated@example.com");

    }

    @Test
    @Order(4)
    @Rollback(value = false)
    @DisplayName("Delete user test")
    public void deleteUserTest() {

        var user = userRepository.findById(1).orElseThrow();

        userRepository.delete(user);

        User userNull = null;

        var optionalUser = userRepository.findById(1);

        if (optionalUser.isPresent()) {
            userNull = optionalUser.get();
        }

        Assertions.assertThat(userNull).isNull();
    }

    @Test
    @Order(5)
    @DisplayName("Find users by birth date range test")
    public void getByBirthRangeTest() {
        User user1 = User.builder()
                .email("test1@example.com")
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(1990, 1, 1))
                .address("123 Main St")
                .phoneNumber("1234567890")
                .build();
        User user2 = User.builder()
                .email("test2@example.com")
                .firstName("Jane")
                .lastName("Doe")
                .birthDate(LocalDate.of(1991, 1, 1))
                .address("123 Main St")
                .phoneNumber("1234567890")
                .build();
        userRepository.save(user1);
        userRepository.save(user2);

        List<User> users = userRepository.findByBirthDateBetween(LocalDate.of(1989, 12, 31), LocalDate.of(1991, 1, 2));

        Assertions.assertThat(users.size()).isEqualTo(2);
        Assertions.assertThat(users).contains(user1, user2);
    }

    @Test
    @Order(6)
    @DisplayName("Find user by email test")
    public void findUserByEmailTest() {
        User user = User.builder()
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(1990, 1, 1))
                .address("123 Main St")
                .phoneNumber("1234567890")
                .build();
        userRepository.save(user);

        User foundUser = userRepository.findUserByEmail("test@example.com");

        Assertions.assertThat(foundUser).isNotNull();
        Assertions.assertThat(foundUser.getEmail()).isEqualTo("test@example.com");
    }
}
