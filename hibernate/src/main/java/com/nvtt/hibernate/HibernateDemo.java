/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nvtt.hibernate;

import com.nvtt.hibernate.pojo.Category;
import com.nvtt.hibernate.pojo.Product;
import com.nvtt.hibernate.pojo.Tag;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
            
            
            // demo mối quan hệ tag, product, category với tag là một bảng trung gian -> tức là chứa 2 key product và cate unique 
//            Category category_ = session.getReference(Category.class, 88); // 1 câu truy vấn
//            Product p = new Product();
//            p.setActive(Boolean.TRUE);
//            p.setCategoryId(category_);
//            p.setPrice(Long.MIN_VALUE);
//            // việc tạo cate ở đây chủ yếu là vì 1 product cần 1 cate để add vào 
//            
//            // dưới này mới là mối quan hệ chín là ManyToMany giữa Product và Tag 
//            Tag t1 = session.get(Tag.class, 1); // câu 2 
//            Tag t2 = session.get(Tag.class, 1); // câu 3
//            
//            Set<Tag> tags = new HashSet<>();
//            tags.add(t1); // câu 5
//            tags.add(t2); // câu 6
//            // => để chèn 2 Tag vào prodTag 
//            
//            p.setTags(tags);
//            
//            session.getTransaction().begin();
//            session.persist(p); // câu 4
//            session.getTransaction().commit();
            
            // ở đây xem thử mối quan hệ này khi persist nó sẽ ra bnh câu truy vấn 
            // ở đây nêú chỉ nhìn sơ qua là 4 ở đây nó chỉ mới lưu product nhưng chưa lưu mqh Tag được gắn vào prod nào 
            // => sẽ có lệnh lưu vào prodTag 
            
            // trong các câu truy vấn được sinh ra có từ escape => đây là cái ko cho các mã độc thực hiện -> giúp convert sang chuỗi
            
            // đây là HQL 
            Query q = session.createQuery("FROM Product", Product.class);
            List<Product> products = q.getResultList();
            
            // đây là criteria
            CriteriaBuilder b = session.getCriteriaBuilder();
            CriteriaQuery<Product> qCri = b.createQuery(Product.class);
            Root root = qCri.from(Product.class);
            qCri.select(root);
            
            // từ đây có thể tạo nhiều Predicate điều kiện để filter -> cuối cùng query 
            Predicate predi = b.like(root.get("name"), "%iphone%");
            Predicate predi1 = b.between(root.get("price"), 28000000, 30000000);
            
            qCri.where(b.or(predi, predi1));
            
            Query q1 = session.createQuery(qCri);
            List<Product> products1 = q1.getResultList();
            
            // phức tạp hơn có join 
            
            // làm thủ công ưu tiên  inner class - đếm 1 danh mục có bao nhiêu sản phẩm 
            // => cần join product và cate -> có 2 root lúc này truy vấn sẽ ko cụ thể trên 1 entity nữa
            CriteriaBuilder b1 = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> qCri1 = b.createQuery(Object[].class); // chưa xác định được kết quả đầu ra là thuộc cái nào vì lấy ra id cate, name cate, count product
            Root root1 = qCri1.from(Product.class);
            Root root2 = qCri1.from(Category.class);
            
            // lấy 3 trường thuộc 2 bảng khác nhau Select
            qCri1.multiselect(root2.get("id"), root2.get("name"), b1.count(root1.get("id")));
            
            // Where: tương tự làm cổ điển tức là lấy khóa ngoại của Product so với khóa chính của cate 
            qCri1.where(b1.equal(root1.get("category").as(Integer.class), root2.get("id"))); // phiên bản cũ thì ko cần .as nhưng 6 trở đi cần 
            
            // gom nhóm 
            qCri1.groupBy(root2.get("id"));
            
            Query query1 = session.createQuery(qCri1);
            List<Object[]> products2 = query1.getResultList();
            
            for (var p : products2) {
                System.out.printf("%d - %s - %.1f\n", p[0], p[1], p[2]);
            }
            // ở đây muốn truyền tên dùng như đối tượng vào ko cần lấy chỉ số có thể parse cấu trúc map vào 
            
            // đối với cách truy vấn join truyền thống như này chỉ có tác dụng với inner join chứ nếu làm left hoặc right join thì sẽ ko được
            
            
            // cách hiện đại dùng Join và root bắt nguồn từ cái muốn đếm 
            CriteriaBuilder builder_ = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> query_ = builder_.createQuery(Object[].class);
            
            // gọi join -> lấy root gọi join với bảng nào manytoone và kiểu join: right join ưu tiên cho cate vì ko có sp vẫn đếm
            Root root_ = query_.from(Product.class); // đếm số product trong 1 cate -> thì để product vào đây 
            Join<Product, Category> join_ = root_.join("category", JoinType.RIGHT);
            // từ đây mọi trường của product lấy qua root_, còn category lấy qua join_
            
        }
        
    }
}
