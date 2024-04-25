package com.example.clearsolutiontestapp.service;

import com.example.clearsolutiontestapp.domain.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface UserService {

    User create(User employee);

    User updateSomeFields(Integer id, Map<String, Object> fieldsToUpdate) throws Exception;

    User updateAllFields(Integer id, User user) throws Exception;

    void delete(Integer id) throws Exception;

    List<User> getByBirthRange(LocalDate startDate, LocalDate endDate);

}
