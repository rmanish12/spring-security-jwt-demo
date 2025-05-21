package com.demo.springsecuritydemo.service;

import com.demo.springsecuritydemo.entity.UserInfo;
import com.demo.springsecuritydemo.repository.UserInfoRepository;
import com.demo.springsecuritydemo.security.UserInfoDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserInfoService implements UserDetailsService {

    private UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userDetails = userInfoRepository.findByEmail(username);

        // Converting UserInfo to UserDetails
        return userDetails
                .map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }


}
