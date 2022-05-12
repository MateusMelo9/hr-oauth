package com.devmelo.hroauth.service;

import com.devmelo.hroauth.domain.User;
import com.devmelo.hroauth.feignclients.UserFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserFeignClient userFeignClient;

    public User findUserByEmail(String email){
        User user = userFeignClient.findUserByEmail(email).getBody();
        if(user == null){
            logger.error("Email not found: {}",email);
            throw new IllegalArgumentException("Email not found");
        }
        logger.info("Email found: {}",email);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userFeignClient.findUserByEmail(email).getBody();
        if(user == null){
            logger.error("Email not found: {}",email);
            throw new UsernameNotFoundException("Email not found");
        }
        logger.info("Email found: {}",email);
        return user;
    }
}
