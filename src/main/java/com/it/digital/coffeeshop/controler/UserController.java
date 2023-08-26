package com.it.digital.coffeeshop.controler;


import com.it.digital.coffeeshop.model.dto.UserDetailsDto;
import com.it.digital.coffeeshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    @Autowired
    private UserService userService;

    @PutMapping
    public ResponseEntity addNewUser(@RequestBody UserDetailsDto user) throws Exception {
        boolean isExistingUser = userService.isExistingCustomer(user);
        if (!isExistingUser) {
            userService.saveUser(user);
        } else {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity updateUser(@RequestBody UserDetailsDto user) throws Exception {
        userService.updateUser(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity deleteUser(@RequestBody UserDetailsDto user) throws Exception {
        boolean isExistingUser = userService.isExistingCustomer(user);
        if (!isExistingUser) {
            userService.deleteUser(user);
        } else {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserDetailsDto> getUserById(@PathVariable Long userId) throws Exception {
        UserDetailsDto user = userService.getUserDetailsDtoById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/customers")
    public ResponseEntity<List<UserDetailsDto>> listCustomers() {
        List<UserDetailsDto> userDetailsDtoList = userService.getAllCustomers();
        if (userDetailsDtoList == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(userDetailsDtoList, HttpStatus.OK);
    }


}
