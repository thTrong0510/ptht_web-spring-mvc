/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nvtt.repositories.impl;


import com.nvtt.hibernate.HibernateUtils;
import com.nvtt.hibernate.pojo.OrderDetail;
import com.nvtt.hibernate.pojo.Product;
import com.nvtt.hibernate.pojo.SaleOrder;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author huu-thanhduong
 */
public class StatsRepositoryImpl {
    
    
    // thống kê doanh thu theo sản phẩm - vì đây là join -> ko biết bảng nào nên dùng Object 
    public List<Object[]> statsrevenueByProduct() {
        try (Session s = HibernateUtils.getFactory().openSession()) {
            CriteriaBuilder b = s.getCriteriaBuilder();
            CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
            
            // nguyên tắc của join là bắt đầu từ đứa chứa khóa ngoại 
            // chỉ định bảng cần join vào - lúc này mình ưu tiên sản phẩm tức là sp ko được bán vẫn thống kê -> right join 
            Root root = q.from(OrderDetail.class);
            Join<OrderDetail, Product> join = root.join("productId", JoinType.RIGHT);
            
            // từ đây muốn lấy thông tin của OrderDetails lấy từ q, product lấy từ join 
            
            // chỉ lấy tên và giá chứ ko lấy tất cả các trường - prod là nhân - sum là cộng 
            q.multiselect(join.get("id"), join.get("name"),
                    b.sum(b.prod(root.get("unitPrice"), root.get("quantity"))));
            q.groupBy(join.get("id"));
            
            Query query = s.createQuery(q);
            return query.getResultList();
        }
    }
    
    // theo ngày tháng -> OrderDetail và SaleOrder vì SaleOrder có thời gian tạo 
    public List<Object[]> statsRevenueByTime(String time, int year) {
        try (Session s = HibernateUtils.getFactory().openSession()) {
            CriteriaBuilder b = s.getCriteriaBuilder();
            CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
            
            Root root = q.from(OrderDetail.class);
            Join<OrderDetail, SaleOrder> join = root.join("orderId", JoinType.INNER);
            
            q.multiselect(b.function(time, Integer.class, join.get("createdDate")), 
                    b.sum(b.prod(root.get("unitPrice"), root.get("quantity"))));
            
            q.where(b.equal(b.function("YEAR", Integer.class, join.get("createdDate")), year));
            
            q.groupBy(b.function(time, Integer.class, join.get("createdDate")));
            
            Query query = s.createQuery(q);
            return query.getResultList();
        }
    }
    
    // sau này nếu thống kế theo sản phẩm và lọc theo ngày tháng thì pải join cả 3 bảng trên 
}
