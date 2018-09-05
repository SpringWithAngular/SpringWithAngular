package me.spring.controller;

import me.spring.entity.User;
import me.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public ResponseEntity<User> handleFileUpload(@RequestParam("file") MultipartFile file){
        User user = new User();
        String content = file.getContentType();
        if(!content.equals("image/png") && !content.equals("image/jpeg")){
            final Exception deu_ruim = new Exception("Deu ruim");
        }else {
            try {
                user = this.userService.uploadFile(file);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }


}
