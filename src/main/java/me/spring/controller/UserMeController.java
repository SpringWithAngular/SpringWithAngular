package me.spring.controller;

import me.spring.entity.User;
import me.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/me/user")
public class UserMeController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<UserDetails> me(){
        return new ResponseEntity<>(userService.getUserLogged(), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<User> edit(@RequestBody User user){
        return new ResponseEntity<User>(this.userService.updateUserLogged(user), HttpStatus.OK);
    }

}
