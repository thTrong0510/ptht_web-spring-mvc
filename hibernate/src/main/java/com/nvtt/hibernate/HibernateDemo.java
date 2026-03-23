/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nvtt.hibernate;

import com.nvtt.hibernate.pojo.Category;
import jakarta.persistence.Query;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author vthan
 */
public class HibernateDemo {
    public static void main(String[] args) {
        // đầu tiên cần tạo session -> từ FACTORY
        try(Session session = HibernateUtils.getFactory().openSession()) {
            // dùng HQL ở đây đang truy vấn trên lớp đối tượng -> cần ghi đúng tên ko được ghi category
//            Query query = session.createQuery("FROM Category", Category.class);
            
            // đối với jdbc -> kết quả trả về là cursor mình phải đóng gói về resultSet -> duyệt
            // nhưng đối với HQL nó tự đóng gói thành list cho chúng ta luôn 
//            List<Category> cates = query.getResultList();
//            
//            for(Category cate : cates){
//                System.out.printf("%d - %s\n", cate.getId(), cate.getName());
//            }
            Category c = new Category(); // transient 
            c.setId(88);
            c.setName("test");
            
            // nếu chỉ có lệnh này nó sẽ ko thể thực hiện tạo SQL lưu xuống database được 
            // vì đây là hành động tranh chấp dữ liệu -> cần transaction 
            // đối với console thì tự bật - web sẽ có cách tự động bật cho 1 method hoặc 1 class
            session.getTransaction().begin();
            session.persist(c); // đang ở buffer 
            session.getTransaction().commit(); // ánh xạ xuống db 
            // persitent
            
            Category category = session.getReference(Category.class, 88);
            //persistent 
            category.setDescription("test update");
            session.getTransaction().begin();
            session.merge(category);
            session.getTransaction().commit();
            
        }
        
    }
}
