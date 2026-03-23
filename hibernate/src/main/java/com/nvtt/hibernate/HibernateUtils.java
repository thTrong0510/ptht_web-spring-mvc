/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nvtt.hibernate;

import com.nvtt.hibernate.pojo.Category;
import com.nvtt.hibernate.pojo.Comment;
import com.nvtt.hibernate.pojo.OrderDetail;
import com.nvtt.hibernate.pojo.ProdTag;
import com.nvtt.hibernate.pojo.Product;
import com.nvtt.hibernate.pojo.SaleOrder;
import com.nvtt.hibernate.pojo.Tag;
import com.nvtt.hibernate.pojo.User;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author vthan
 */
public class HibernateUtils {
    // session factory chỉ dùng 1 hệ qtri -> có 1 cái -> dùng singleton đưa ra cho các class khác dùng
    // về nguyên tắc để tạo FACTORY cần conf -> mà conf chỉ nạp cấu hình lên
    private static final SessionFactory FACTORY;
    
    // cơ chế là chỉ chạy 1 lần duy nhất khi lớp này nạp lên -> có thể tạo conf trong này 
    static {
        Configuration conf = new Configuration();
        // conf thêm về hệ quản trị để tạo ra FACTORY tương ứng
        Properties props = new Properties(); 
        props.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect"); // chỉ định hệ quản trị 
        props.put(Environment.JAKARTA_JDBC_DRIVER, "com.mysql.cj.jdbc.Driver"); 
        props.put(Environment.JAKARTA_JDBC_URL, "jdbc:mysql://localhost/saledb"); // không cần đặt như url = ... vì đây nó có sẵn các thuộc tính static tương ứng
        props.put(Environment.JAKARTA_JDBC_USER, "root");
        props.put(Environment.JAKARTA_JDBC_PASSWORD, "Thanhtrong@0510");
        props.put(Environment.SHOW_SQL, "true");
        
        conf.setProperties(props);
        
        // hiện tại đang dùng console nên nó ko biết lớp persistent mình để ở đâu 
        // qua hệ thống web mình có thể cấu hình cái gói cần quét 
        conf.addAnnotatedClass(Category.class);
        conf.addAnnotatedClass(Product.class);
        conf.addAnnotatedClass(SaleOrder.class);
        conf.addAnnotatedClass(OrderDetail.class);
        conf.addAnnotatedClass(Comment.class);
        conf.addAnnotatedClass(Tag.class);
        conf.addAnnotatedClass(ProdTag.class);
        conf.addAnnotatedClass(User.class);
        
        // phải dùng lớp con của nó là StandardServiceRegistryBuilder
        // đây là mẫu thiết kế mẫu builder -> tức là tạo 1 class ko có constructor mà phải thông qua lớp builder con bên trong để tạo
        // mục tiêu là thuận tiện cho việc cung cấp thông tin linh hoạt hơn vào 
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
        //dùng cái này để nạp thêm dữ liệu vào session 
        FACTORY = conf.buildSessionFactory(serviceRegistry);
    }

    /**
     * @return the FACTORY
     */
    public static SessionFactory getFactory() {
        return FACTORY;
    }
}
