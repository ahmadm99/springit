package com.ahmad.springit.Service;

import com.ahmad.springit.domain.User;
import com.ahmad.springit.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User register(User user){
        return user;
    }
    public User save(User user){
        return userRepository.save(user);
    }

    @Transactional //if an error occurs it will rollback the entire action instead of saving a few users then hitting an error
    public void saveUsers(User... users){
        for(User user : users){
            logger.info("Saving users: "+ user.getEmail());
            userRepository.save(user);
        }
    }


}
