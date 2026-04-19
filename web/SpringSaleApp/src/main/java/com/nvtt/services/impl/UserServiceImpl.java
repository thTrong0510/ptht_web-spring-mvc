/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nvtt.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nvtt.pojo.User;
import com.nvtt.repositories.UserRepository;
import com.nvtt.services.UserService;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author vthan
 */
@Service("userDetailsService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public User getUserByUsername(String username) {
        return this.userRepository.getUserByUsername(username);
    }

    @Override
    public User addOrUpdateUser(Map<String, String> params, MultipartFile avatar) {
        User user = new User();
        user.setFirstName(params.get("firstName"));
        user.setLastName(params.get("lastName"));
        user.setEmail(params.get("email"));
        user.setUsername(params.get("userName"));
        user.setPhone(params.get("phone"));
        user.setPassword(this.passwordEncoder.encode(params.get("password")));
        user.setUserRole("ROLE_USER");

        try {
            // resource type: auto -> là để cloudinary tự ép kiểu cho từng loại file gửi lên
            Map res = this.cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.asMap("resource type", "auto"));
            user.setAvatar(res.get("secure_url").toString());

            // tới đây cần parser -> cũng cần phải khai báo Bean tương tự 
        } catch (IOException ex) {
            Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("errorr");
        }

        this.userRepository.addOrUpdateUser(user);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.getUserByUsername(username);
        
        if(user == null) {
            throw new UsernameNotFoundException("invalid username");
        }
        
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getUserRole()));
        
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

}
