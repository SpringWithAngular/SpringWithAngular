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
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        String response = "Upload feito com sucesso";
        String content = file.getContentType();
        if(!content.equals("image/png") && !content.equals("image/jpeg")){
            response = "Deu ruim";
        }else {
            try {
                this.userService.uploadFile(file);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return response;
    }


}
