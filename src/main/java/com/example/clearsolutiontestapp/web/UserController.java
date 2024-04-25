package com.example.clearsolutiontestapp.web;

import com.example.clearsolutiontestapp.domain.User;
import com.example.clearsolutiontestapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@RequestBody User user) {
        log.info("UserController --> saveUser() - start: user = {}", user);
        User savedUser = userService.create(user);
        log.info("UserController --> saveUser() - end: user = {}", savedUser);
        return savedUser;
    }

    @SneakyThrows
    @PutMapping("/{id}/fields")
    @ResponseStatus(HttpStatus.OK)
    public User updateSomeUsersFields(@PathVariable("id") String id, @RequestBody Map<String, Object> fieldsToUpdate) {
        log.info("UserController --> updateSomeUsersFields() - start: id = {}, fieldsToUpdate = {}:", id, fieldsToUpdate);
        Integer parseId = Integer.parseInt(id);
        User user = userService.updateSomeFields(parseId, fieldsToUpdate);
        log.info("UserController --> updateSomeUsersFields() - end: user = {}", user);
        return user;
    }

    @SneakyThrows
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User updateAllUsersFields(@PathVariable("id") String id, @RequestBody User user) {
        log.info("UserController --> updateAllUsersFields() - start: id = {}, user = {}:", id,user);
        Integer parseId = Integer.parseInt(id);
        User updatedEmployee= userService.updateAllFields(parseId, user);
        log.info("UserController --> updateAllUsersFields() - end: user = {}", updatedEmployee);
        return updatedEmployee;
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeUser(@PathVariable("id") String id) {
        log.info("UserController --> removeUserById() - start: id = {}", id);
        Integer parseId = Integer.parseInt(id);
        userService.delete(parseId);
        log.info("UserController --> removeUserById() - end");
    }

    @GetMapping("/birthdate")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUsersByBirthRange(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                           @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        log.info("UserController --> getUsersByBirthRange() - start: startDate = {}, endDate = {}", startDate, endDate);
        List<User> users = userService.getByBirthRange(startDate, endDate);
        log.info("UserController --> getUsersByBirthRange() - end: users = {}", users);
        return users;
    }

}
