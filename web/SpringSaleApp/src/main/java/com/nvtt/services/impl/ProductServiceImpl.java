/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nvtt.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nvtt.pojo.Product;
import com.nvtt.repositories.ProductRepository;
import com.nvtt.services.ProductService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author vthan
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private Cloudinary cloudinary;
    
    @Override
    public List<Product> getProducts(Map<String, String> params) {
        return this.productRepository.getProducts(params);
    }
    
    @Override
    public void addOrUpdateProduct(Product p) {
        if(!p.getFile().isEmpty()) {
            try {
                // resource type: auto -> là để cloudinary tự ép kiểu cho từng loại file gửi lên
                Map res = this.cloudinary.uploader().upload(p.getFile().getBytes(), ObjectUtils.asMap("resource type", "auto"));
                p.setImage(res.get("secure_url").toString());
                
                // tới đây cần parser -> cũng cần phải khai báo Bean tương tự 
            } catch (IOException ex) {
                Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("errorr");
            }
        }
        this.productRepository.addOrUpdateProduct(p);
    }
    
    @Override
    public Product getProductById(int id) {
        return this.productRepository.getProductById(id);
    }
    
    @Override
    public void deleteProduct(int id) {
        this.productRepository.deleteProduct(id);
    }
    
}
