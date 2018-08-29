package me.spring.config;


import me.spring.entity.Role;
import me.spring.entity.User;
import me.spring.repository.RoleRepository;
import me.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializr implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {

        this.createRoles();
        Role roleUser = roleRepository.findByName("ROLE_USER");
        Role roleAmin = roleRepository.findByName("ROLE_ADMIN");

        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            createUser("Nataniel", "nataniel.paiva@gmail.com", passwordEncoder.encode("123"), roleAmin);
//            for(int i = 0; i < 200; i++){
//                String generatedString = this.randomAlphaNumeric(7);
//                User user = new User(generatedString,  generatedString+"@gmail.com",
//                        passwordEncoder.encode("123"), Arrays.asList(roleUser));
//                users.add(user);
//            }
            userRepository.saveAll(users);
        }
    }

    private void createRoles(){
        Role role = new Role("ROLE_ADMIN");
        this.roleRepository.save(role);

        Role roleUser = new Role("ROLE_USER");
        this.roleRepository.save(roleUser);
    }

    private void createUser(String name, String email, String password, Role role) {
        User user = new User(name, email, password, Arrays.asList(role));
        userRepository.save(user);
    }

    private String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

}

