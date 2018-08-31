package me.spring.service;

import me.spring.entity.User;
import me.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserDetails getUserLogged(){

        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public User updateUserLogged(User user){
        UserDetails userLogged = this.getUserLogged();
        User userUpdate = userRepository.findByEmail(userLogged.getUsername());
        userUpdate.setName(user.getName());
        userUpdate.setPhone(user.getPhone());
        userUpdate.setEmail(user.getEmail());
        userUpdate.setHouse_number(user.getHouse_number());
        userUpdate.setQuantity_car(user.getQuantity_car());
        return userRepository.save(userUpdate);
    }

}
