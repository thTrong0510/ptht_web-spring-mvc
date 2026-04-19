/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nvtt.controllers;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author vthan
 */
@Controller
@RequestMapping("/admin")
public class UserController {
    // có thể dùng Principal để check xem đn hay chưa 
    
    @GetMapping("/login")
    public String loginView() {
        return "login";
    }
}
