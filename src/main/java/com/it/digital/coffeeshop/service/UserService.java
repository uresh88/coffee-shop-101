package com.it.digital.coffeeshop.service;

import com.it.digital.coffeeshop.dao.models.User;
import com.it.digital.coffeeshop.exception.NotFoundException;
import com.it.digital.coffeeshop.model.dto.UserDetailsDto;

import java.util.List;

public interface UserService {
    boolean isExistingCustomer(UserDetailsDto userDetailsDto) throws Exception;
    void saveUser(UserDetailsDto userDetailsDto);
    void updateUser(User user);

    void deleteUser(UserDetailsDto user) throws NotFoundException;

    UserDetailsDto getUserDetailsDtoById(Long userId) throws NotFoundException;

    List<UserDetailsDto> getAllCustomers();

    User getUserById(Long userId) throws NotFoundException;

    void updateUser(UserDetailsDto userDetailsDto) throws NotFoundException;

}


