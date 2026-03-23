/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nvtt.controllers;

import com.nvtt.pojo.Product;
import com.nvtt.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author vthan
 */
@Controller
@RequestMapping("/admin")
public class ProductController {
    @Autowired
    private ProductService productService;
    
    @GetMapping("/products")
    public String createView(Model model) {
        model.addAttribute("product", new Product());
        return "product";
    }
    
    @PostMapping("/products")
    public String create(Model model, @ModelAttribute(value = "product") Product product) {
        return "product";
    }
}
