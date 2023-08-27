package com.it.digital.coffeeshop.service.impl;

import com.it.digital.coffeeshop.dao.UserRepository;
import com.it.digital.coffeeshop.dao.models.User;
import com.it.digital.coffeeshop.exception.NotFoundException;
import com.it.digital.coffeeshop.model.dto.UserDetailsDto;
import com.it.digital.coffeeshop.model.enums.UserType;
import com.it.digital.coffeeshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    /*
    * if user found by ---- **mobile_num [or] **id ---then return true
    * check if user id exist and find user by id if user present return true
    * then check mobile number exists if mobile number is null it's an error
    * */
    @Override
    public boolean isExistingCustomer(UserDetailsDto userDetailsDto) throws Exception {
        if(userDetailsDto.getId() != null){
            Optional<User> user = userRepository.findById(userDetailsDto.getId());
            if(user.isPresent()){
                return true;
            }
        }
        if(userDetailsDto.getMobileNumber() != null){
            List<User> userList = userRepository.findAllByMobileNumber(userDetailsDto.getMobileNumber());
            return userList != null && userList.size() > 0;
        }else {
            throw new Exception("Mobile number not existing error");
        }
    }

    @Override
    public void saveUser(UserDetailsDto userDetailsDto) {
        User user = new User();
        this.userDetailToUser(userDetailsDto,user);
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteUser(UserDetailsDto userDetailsDto) throws NotFoundException {
        User user = getUserById(userDetailsDto.getId());
        this.userRepository.delete(user);
    }

    @Override
    public UserDetailsDto getUserDetailsDtoById(Long userId) throws NotFoundException {
        Optional<User> optUser = this.userRepository.findById(userId);
        if(optUser.isPresent()){
            User user = optUser.get();
            return this.userToUserDetail(user);
        }
        throw new NotFoundException("User not found");
    }

    @Override
    public User getUserById(Long userId) throws NotFoundException {
        Optional<User> optUser = this.userRepository.findById(userId);
        if(optUser.isPresent()){
            User user = optUser.get();
            return user;
        }
        throw new NotFoundException("User not found");
    }

    @Override
    public void updateUser(UserDetailsDto userDetailsDto) throws NotFoundException {
        User user = getUserById(userDetailsDto.getId());
        this.userDetailToUser(userDetailsDto, user);
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public List<UserDetailsDto> getAllCustomers() {
        List<User> userList = this.userRepository.findAllByUserType(UserType.CUSTOMER);
        List<UserDetailsDto> userDetailsDtoList = userList.stream().map(this::userToUserDetail).collect(Collectors.toList());
        return userDetailsDtoList;
    }

    private void userDetailToUser(UserDetailsDto userDetailsDto, User user) {
        user.setName(userDetailsDto.getName());
        user.setUserType(userDetailsDto.getUserType());
        user.setMobileNumber(userDetailsDto.getMobileNumber());
        user.setRegularAddress(userDetailsDto.getRegularAddress());
    }
    private UserDetailsDto userToUserDetail(User user ) {
        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setId(user.getId());
        userDetailsDto.setName(user.getName());
        userDetailsDto.setRegularAddress(user.getRegularAddress());
        userDetailsDto.setUserType(user.getUserType());
        userDetailsDto.setMobileNumber(user.getMobileNumber());
        userDetailsDto.setTotalPoints(user.getTotalPoints());
        return userDetailsDto;
    }

}
