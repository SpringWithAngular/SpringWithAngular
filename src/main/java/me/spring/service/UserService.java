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
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
        userUpdate.setHouseNumber(user.getHouseNumber());
        userUpdate.setQuantityCar(user.getQuantityCar());
        return userRepository.save(userUpdate);
    }

    public User uploadFile(MultipartFile file) throws IOException{
        UserDetails userDetails = this.getUserLogged();
        User user = this.userRepository.findByEmail(userDetails.getUsername());
        try {
            File fileLocal = this.convert(file);
            BufferedImage bimg = ImageIO.read(fileLocal);
            int width          = bimg.getWidth();
            if(width > 100){
                s3client.putObject(new PutObjectRequest(this.bucket, "original/" + file.getOriginalFilename(),  fileLocal));
                BufferedImage image = ImageIO.read(fileLocal);
                BufferedImage resized = this.resize(image, 50, 50);
                File output = new File("target/image.png");
                ImageIO.write(resized, "png", output );

                s3client.putObject(new PutObjectRequest(this.bucket, file.getOriginalFilename(),  output));
            }else{
                s3client.putObject(new PutObjectRequest(this.bucket, file.getOriginalFilename(),  fileLocal));
                s3client.putObject(new PutObjectRequest(this.bucket, "original/" + file.getOriginalFilename(),  fileLocal));
            }


            user.setPictureUrl(this.url + file.getOriginalFilename());
            user.setPictureUrlOriginal(this.url + "original/" + file.getOriginalFilename());
            this.userRepository.save(user);

        } catch (AmazonClientException ace) {
            logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return user;
    }

    public File convert(MultipartFile file) throws IOException
    {
        File convFile = new File("target/" + file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }


}
