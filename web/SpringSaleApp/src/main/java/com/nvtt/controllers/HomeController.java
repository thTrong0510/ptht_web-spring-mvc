/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nvtt.controllers;

import com.nvtt.services.CategoryService;
import com.nvtt.services.ProductService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author vthan
 */
@Controller
@ControllerAdvice
public class HomeController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    public void commonResponse(Model model) {
        model.addAttribute("categories", this.categoryService.getCates());
    }

    @RequestMapping("/")
    public String HomePage(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("products", this.productService.getProducts(params));
        return "index";
    }
}
