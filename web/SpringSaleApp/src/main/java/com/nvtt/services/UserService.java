/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nvtt.services;

import com.nvtt.pojo.User;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author vthan
 */
public interface UserService {
    public User getUserByUsername(String username);
    public User addOrUpdateUser(Map<String, String> params, MultipartFile avatar);
}
