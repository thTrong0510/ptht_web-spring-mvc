/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nvtt.services;

import com.nvtt.pojo.Product;
import java.util.List;
import java.util.Map;

/**
 *
 * @author vthan
 */
public interface ProductService {
    public List<Product> getProducts(Map<String, String> params);
}
