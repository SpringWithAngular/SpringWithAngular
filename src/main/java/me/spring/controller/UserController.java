package me.spring.controller;

import me.spring.entity.User;
import me.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<User> save(@RequestBody User user){
        return new ResponseEntity<User>(this.userRepository.save(user), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<User> edit(@RequestBody User user){
        return new ResponseEntity<User>(this.userRepository.save(user), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Page<User>> list(Pageable pageable){
        return new ResponseEntity<Page<User>>(userRepository.findAll(pageable), HttpStatus.OK);
    }

    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    public ResponseEntity<Page<User>> search(Pageable pageable, @PathVariable String name){
        return new ResponseEntity<Page<User>>(userRepository.findByNameContainingIgnoreCase(name, pageable), HttpStatus.OK);
    }

}
