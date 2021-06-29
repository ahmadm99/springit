package com.ahmad.springit.Service;

import com.ahmad.springit.domain.User;
import com.ahmad.springit.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final RoleService roleService;
    private final MailService mailService;

    public UserService(UserRepository userRepository, RoleService roleService, MailService mailService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.mailService = mailService;
        encoder = new BCryptPasswordEncoder();
    }

    public User register(User user) {
        //take pw and encode it
        String secret = "{bcrypt}" + encoder.encode(user.getPassword());
        user.setPassword(secret);
        //confirm password
        user.setConfirmPassword(secret);
        //assign a role to this user
        user.addRole(roleService.findByName("ROLE_USER"));
        //set an activation code
        user.setActivationCode(UUID.randomUUID().toString());
        //disable the user
        //save the user
        save(user);
        //send activation email
        //return the user
        return user;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void sendActivationEmail(User user){
        mailService.sendActivationEmail(user);
    }
    public void sendWelcomeEmail(User user){
        mailService.sendWelcomeEmail(user);
    }

    @Transactional
    //if an error occurs it will rollback the entire action instead of saving a few users then hitting an error
    public void saveUsers(User... users) {
        for (User user : users) {
            logger.info("Saving users: " + user.getEmail());
            userRepository.save(user);
        }
    }

    public Optional<User> findByEmailAndActivationCode(String email, String activationCode){
        return userRepository.findByEmailAndActivationCode(email,activationCode);
    }

}
