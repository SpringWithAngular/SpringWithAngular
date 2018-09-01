package me.spring.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import me.spring.entity.User;
import me.spring.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;

@Service
public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private AmazonS3 s3client;

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Value("${aws.cloudfront.url}")
    private String url;

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

    public void uploadFile(String uploadFilePath, String fileName) {

        try {
            File file = new File(uploadFilePath);
            s3client.putObject(new PutObjectRequest(this.bucket, fileName,  file));

        } catch (AmazonClientException ace) {
            logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
        }
    }


}
