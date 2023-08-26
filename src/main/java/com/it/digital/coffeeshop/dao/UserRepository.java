package com.it.digital.coffeeshop.dao;

import com.it.digital.coffeeshop.dao.models.User;
import com.it.digital.coffeeshop.model.enums.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByMobileNumber(String mobileNumber);
    List<User> findAllByUserType(UserType userType);
}
