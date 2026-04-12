/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nvtt.controllers;

import com.nvtt.pojo.Product;
import com.nvtt.services.CategoryService;
import com.nvtt.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author vthan
 */
@Controller
@RequestMapping("/admin")
//@ControllerAdvice
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    // ở đây dữ liệu categories được sử dụng lại nhiều ở các view 
    // annotation này có 2 công dụg: 
    // 1 là nhận dữ liệu body từ form binding lên  
    // 2 dùng áp dụng cho phương thức -> tất cả các thuộc tính add lên model phản hồi cho mọi response chung class
    // muốn những controller khác cũng có thông tin này thì dùng thêm @ControllerAdvice 
//    public void commonResponse(Model model) {
//        model.addAttribute("categories", this.categoryService.getCates());
//    }
    // thêm mới
    @GetMapping("/products")
    public String createView(Model model) {
        model.addAttribute("product", new Product());
        return "product";
    }

    @PostMapping("/products")
    public String create(Model model, @ModelAttribute(value = "product") Product product) {
        try {
            this.productService.addOrUpdateProduct(product);
            return "redirect:/";
            // trong java có 2 cách điều hướng: (ở các công nghệ khác chỉ có từ trang này -> trang khác) 
            // - redirect: request ở trang đầu ~ ko liên quan gì đến trang sau 
            // - forward: cũng chuyển trang nhưng dùng chung 1 request 
        } catch (Exception ex) {
            System.err.println(ex);
            return "/product";
        }

    }

    // cập nhật - nguyên tắc là thêm id của đường dẫn vào
    @GetMapping("/products/{id}")
    public String updateView(Model model, @PathVariable(value = "id") int id) {
        Product product = this.productService.getProductById(id);
        model.addAttribute("product", product);
        return "product";
    }
}
