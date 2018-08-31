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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get("C:\\Users\\natan\\Pictures\\" + file.getOriginalFilename());
            Files.write(path, bytes);
            String pathFile =  "C:\\Users\\natan\\Pictures\\" + file.getOriginalFilename();
//            this.fileService.uploadFile("AKIAIT34F7FGVNAKKHKA", pathFile, file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Upload feito";
    }


}
