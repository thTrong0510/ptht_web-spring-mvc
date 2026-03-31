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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    // khi chạy nó sẽ nạp -> context, rỗ đậu, dispatcher, các hạt đậu, chạy vào trang chủ -> cấu hình thymeleaf -> vào file index -> render 
    @RequestMapping("/")
    public String HomePage(Model model, @RequestParam Map<String, String> params) {
        // dùng request param kiểu này là thay cho bên dưới trong trường hợp 
        // ko biết số lượng params truyền vào cũng như là có truyền hay là ko
        // dùng Map đây là cách lấy tổng quát -> biến dữ liệu thành dạng băm 
        // mọi thứ phải là chuỗi chuyển về key,value 
        // lấy = get("key")/getOrDefault...
        
        model.addAttribute("products", this.productService.getProducts(params));
        return "index";
    }
    
    // request param
//        @RequestMapping("/")
//    public String HomePageRequestParam(Model model, 
//            @RequestParam(value = "firstName", required = false, defaultValue = "none") String fn,
//            @RequestParam(value = "lastName", required = false, defaultValue = "none") String ln) {
//        // do some things 
//        // mặc định RequestParam này sẽ là bắt buộc phải truyền nếu ko sẽ bị lỗi -> tránh thì có thể set required = false
//        // hoặc set default value 
//        // ưu nhanh vì ánh xạ đụng cái biến cần lấy - khuyết là dài dòng 
//        // => nếu định hình được là truyền lên cái gì và ít tham số thì nên dùng cái này 
//        return "index";
//    }
    
    // 2 dạng trên thì url: ?key=value&key=value
    
    // tiếp đến dạng /{...} nằm trực tiếp trên đường dẫn - /hello/Trong
    // ảnh hưởng trực tiếp đến ánh xạ đường dẫn -> bắt buộc phải gửi 
    @GetMapping("/hello/{name}")
    public String HomePageModelAttribute(@PathVariable(value = "name") String name) {
        
        return "index";
    }
}
