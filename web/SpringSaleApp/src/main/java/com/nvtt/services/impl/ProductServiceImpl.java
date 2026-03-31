/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nvtt.services.impl;

import com.nvtt.pojo.Product;
import com.nvtt.repositories.ProductRepository;
import com.nvtt.services.ProductService;
import java.util.List;
import java.util.Map;
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
    
    @Override
    public List<Product> getProducts(Map<String, String> params) {
        return this.productRepository.getProducts(params);
    }
    
    @Override
    public void addOrUpdateProduct(Product p) {
        this.productRepository.addOrUpdateProduct(p);
    }
    
    @Override
    public Product getProductById(int id) {
        return this.productRepository.getProductById(id);
    }
    
}
