/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nvtt.controllers;

import com.nvtt.pojo.Product;
import com.nvtt.services.ProductService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author vthan
 */
@RestController
@RequestMapping("/api")
@CrossOrigin // cho toàn bộ API trong controller - toàn bộ dự án thì cấu hình trong webAppContextConfig
public class ApiProductController {
    
    @Autowired
    private ProductService productService;
    
    @GetMapping("/products")
    @CrossOrigin // có thể cấu hình danh sách domain bên trong - hoặc 1 biẻu thức chính quy - nếu để 0 thì cho mọi client 
    public ResponseEntity<List<Product>> getProducts(@RequestParam Map<String, String> params) {
        List<Product> products = this.productService.getProducts(params);
        // nếu để bth như này nó sẽ không chạy được vì quá trình serializer trực tiếp vào pojo
        // hiện tại Product có nhiều các mối quan hệ ràng buộc -> nó cũng seri.. những cái đó luôn 
        // ví dụ Category bên trong Product -> khi seri.. Product nó sẽ seri.. luôn Cate mà trong Cate lại có Prod -> vòng lặp vô hạn -> fail
        // vào trực tiếp Pojo @JsonIgnore những mqh ko cần thiết 
        // ví dụ đối với Product thì chừa lại Category và vào Category Ignore luôn thằng Product để tránh lặp
        
        // lỗi CORS: 
        // nếu ở domain ... vào console: fetch("http://localhost:8080/SpringSaleApp/api/products").then(res => res.json()).then(data => console.log(data)) -> ra dữ liệu bth vì chung domain với server
        // nếu qua lms.ou.edu.vn làm tương tự nó sẽ bị lỗi CORS -> backend/server có thể cấu hình cho domain khác truy cập 
        
        // Khi hiển thị ra thông tin chung thì phải hiện thị hạn chế lại -> khi nào xem detail mới get hết thông tin -> DTO 
        return ResponseEntity.ok(products);
    }
    
    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED) // chỉ phản hồi status chứ ko có dữ liệu 
    public void createProducts(@RequestBody Product product) {
        this.productService.addOrUpdateProduct(product);
    }
    
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") int id) {
        Product product = this.productService.getProductById(id);
        
        // mặt định trong phiên bản cũ nếu trả về 1 đối tượng duy nhất nó sẽ trả về xml
        return ResponseEntity.ok(product);
    }
    
    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductById(@PathVariable("id") int id) {
        this.productService.deleteProduct(id);
    }
}
