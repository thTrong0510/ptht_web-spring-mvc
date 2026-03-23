/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nvtt.repositories.impl;

import com.nvtt.hibernate.HibernateUtils;
import com.nvtt.hibernate.pojo.Product;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;

/**
 *
 * @author vthan
 */
public class ProductRepositoryImpl {
    
    private static final int PAGE_SIZE = 6;
    
    public List<Product> getProducts(Map<String, String> params) {
        try(Session session = HibernateUtils.getFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            // builder này sẽ liên quan đến các hàm truy vấn where, order by, and or ...
            
            CriteriaQuery<Product> q = builder.createQuery(Product.class);
            // nếu mình làm chỉ trên 1 pojo thì chỉ trực tiêps cho nó luôn
            
            // root dùng để lấy các trường của 1 pojo class ra 
            Root root = q.from(Product.class);
            
            // đây tương ứng với select all tất cả các trường 
            q.select(root);
            
            // kiểm tra điều kiện lọc 
            if (params != null) {
                List<Predicate> predicates = new ArrayList<>();
                
                String kw = params.get("kw");
                if(kw != null && !kw.isEmpty()) {
                    // truy vấn 1 câu duy nhất 
//                    q.where(builder.like(root.get("name"), String.format("%%%s%%", kw)));

                    // muốn kết hợp truy vấn thì cần add tất cả vào 1 predicates 
                    predicates.add(builder.like(root.get("name"), String.format("%%%s%%", kw)));
                }
                
                String fromPrice = params.get("fromPrice");
                if (fromPrice != null && !fromPrice.isEmpty()) {
                    predicates.add(builder.greaterThanOrEqualTo(root.get("price"), fromPrice)); // không cần chuyển kiểu dữ liệu nó tự làm 
                }
                
                String toPrice = params.get("toPrice");
                if (toPrice != null && !toPrice.isEmpty()) {
                    predicates.add(builder.lessThan(root.get("price"), fromPrice)); // không cần chuyển kiểu dữ liệu nó tự làm 
                }
                
                String cateId = params.get("cateId");
                if (cateId != null && !cateId.isEmpty()) {
                    predicates.add(builder.equal(root.get("categoryId"), cateId)); // không cần chuyển kiểu dữ liệu nó tự làm 
                }
                
                q.where(predicates.toArray(Predicate[]::new));
                // q.orderBy
            }
            
            // lúc trước mình truyển HQL bây 
            Query query = session.createQuery(q);
            
            // phân trang 
            if (params != null && !params.isEmpty()) {
                int page = Integer.parseInt(params.getOrDefault("page", "1"));
                int start = (page - 1) * PAGE_SIZE;
                
                query.setMaxResults(PAGE_SIZE);
                query.setFirstResult(start);
                
            }
            
            return query.getResultList();
        }
    }
    
        public Product getProductById(int id) {
        try (Session s = HibernateUtils.getFactory().openSession()) {
            return s.get(Product.class, id);
        }
    }
    
    public void addOrUpdateProduct(Product p) {
        try (Session s = HibernateUtils.getFactory().openSession()) {
            if (p.getId() == null) {
                s.persist(p);
            } else {
                s.merge(p);
            }
        }
    }
    
    public void deleteProduct(int id) {
        try (Session s = HibernateUtils.getFactory().openSession()) {
            Product p = this.getProductById(id);
            s.remove(p);
        }
    }
}
