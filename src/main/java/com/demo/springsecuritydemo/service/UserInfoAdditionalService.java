package com.demo.springsecuritydemo.service;

import com.demo.springsecuritydemo.entity.UserInfo;
import com.demo.springsecuritydemo.repository.UserInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserInfoAdditionalService {

    private UserInfoRepository userInfoRepository;
//    private PasswordEncoder encoder;

    public String addUser(UserInfo userInfo) {
//        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        userInfoRepository.save(userInfo);
        return "User added successfully";
    }
}
