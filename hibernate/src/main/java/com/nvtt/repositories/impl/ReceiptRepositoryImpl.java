/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nvtt.repositories.impl;

import com.nvtt.hibernate.HibernateUtils;
import com.nvtt.hibernate.pojo.CartItem;
import com.nvtt.hibernate.pojo.OrderDetail;
import com.nvtt.hibernate.pojo.SaleOrder;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author huu-thanhduong
 */
public class ReceiptRepositoryImpl {
    
    // thêm 1 hóa đơn mua hàng 
    // tạo pojo giả vì dữ liệu giỏ hàng là react nó sẽ gửi lên 
    public void addReceipt(List<CartItem> carts) {
        try (Session s = HibernateUtils.getFactory().openSession()) {
            // đầu tiên tạo ra hóa đơn trước nhưng trong hóa đơn cóa user_id -> phải có người đăng nhập 
            
            SaleOrder receipt = new SaleOrder();
            receipt.setUserId(new UserRepositoryImpl().getUserByUsername("dhthanh"));
            receipt.setCreatedDate(new Date());
            s.persist(receipt);
            
            for (var c: carts) {
                OrderDetail d = new OrderDetail();
                d.setQuantity(c.getQuantity());
                d.setUnitPrice(c.getPrice());
                d.setProductId(new ProductRepositoryImpl().getProductById(c.getId()));
                d.setOrderId(receipt);
                
                s.persist(d);
            }
        }
    }
}
