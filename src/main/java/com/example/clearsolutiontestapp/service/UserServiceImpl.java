package com.example.clearsolutiontestapp.service;

import com.example.clearsolutiontestapp.domain.User;
import com.example.clearsolutiontestapp.repository.UserRepository;
import com.example.clearsolutiontestapp.util.CopyDataException;
import com.example.clearsolutiontestapp.util.UnderageException;
import com.example.clearsolutiontestapp.util.UnrealAgeException;
import com.example.clearsolutiontestapp.util.UserIsNotExistException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Value("${user.minimum.age}")
    private int minimumAge;

    @SneakyThrows
    @Override
    public User create(User user) {
        log.debug("UserService --> create() - start: user = {}", user);
        validateInputData(user);
        User newUser = userRepository.save(user);
        log.debug("UserService --> create() - end: user = {}", newUser);
        return newUser;
    }

    @SneakyThrows
    @Override
    public User updateSomeFields(Integer id, Map<String, Object> fieldsToUpdate) {
        log.debug("UserService --> updateSomeFields() - start: id = {}, fieldsToUpdate = {}", id, fieldsToUpdate);
        User updatedUser = userRepository.findById(id)
                .map(entity -> {
                    fieldsToUpdate.forEach((name, value) -> {
                        try {
                            Field field = User.class.getDeclaredField(name);
                            field.setAccessible(true);
                            field.set(entity, value);
                        } catch (NoSuchFieldException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });
                    return userRepository.save(entity);
                })
                .orElseThrow(UserIsNotExistException::new);
        validateInputData(updatedUser);
        log.debug("UserService --> updateSomeFields() - end: user = {}", updatedUser);
        return updatedUser;
    }

    @SneakyThrows
    @Override
    public User updateAllFields(Integer id, User user) {
        log.debug("UserService --> updateAllFields() - start: id = {}, user = {}", id, user);
        validateInputData(user);
        User updatedUser = userRepository.findById(id)
                .map(entity -> {
                    entity.setEmail(user.getEmail());
                    entity.setFirstName(user.getFirstName());
                    entity.setLastName(user.getLastName());
                    entity.setBirthDate(user.getBirthDate());
                    entity.setAddress(user.getAddress());
                    entity.setPhoneNumber(user.getPhoneNumber());
                    return userRepository.save(entity);
                })
                .orElseThrow(UserIsNotExistException::new);
        log.debug("UserService --> updateAllFields() - end: user = {}", updatedUser);
        return updatedUser;
    }

    @SneakyThrows
    @Override
    public void delete(Integer id) {
        log.debug("UserService --> delete() - start: id = {}", id);
        User user = userRepository.findById(id).orElseThrow(UserIsNotExistException::new);
        userRepository.delete(user);
        log.debug("UserService --> delete() - end: user = {}", user);
    }

    @Override
    public List<User> getByBirthRange(LocalDate startDate, LocalDate endDate) {
        log.debug("UserService --> getByBirthRange() - start: startDate = {}, endDate = {}", startDate, endDate);
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        List<User> users = userRepository.findByBirthDateBetween(startDate, endDate);
        log.debug("UserService --> getByBirthRange() - end: users = {}", users);
        return users;
    }

    private void validateInputData(User user) throws CopyDataException {
        int age = Period.between(user.getBirthDate(), LocalDate.now()).getYears();
        if (age > 122) {
            throw new UnrealAgeException();
        }

        if (age < minimumAge) {
            throw new UnderageException();
        }

        User existingUser = userRepository.findUserByEmail(user.getEmail());
        if (existingUser != null) {
            throw new CopyDataException();
        }
    }

}
