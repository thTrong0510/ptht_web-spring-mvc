/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nvtt.configs;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 *
 * @author vthan
 */

public class DispatcherServeletInit extends AbstractAnnotationConfigDispatcherServletInitializer {

    // những class chỉ là rỗ đậu tức là có @configuration nhưng không kế thừa bất kì class nào thì khai báo trong này
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {
            ThymeleafConfig.class,
            HibernateConfigs.class,
            SpringSecurityConfigs.class
        };
    }

    // khai báo các config có kế thừa WebMvcConfigurer
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {
            WebAppContextConfigs.class
        };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
    
    // muốn dùng cloudinary và làm việc với file cần custom lại - để đăng ký 1 multipart file 
    // phiên bản cũ thì nó sẽ thông qua multipart common (file tách riêng bên ngoài chứ k cấu hình trong này)
    
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setMultipartConfig(
               new MultipartConfigElement("/", 50000000, 150000000, 0)
        // 4 tham số lần lượt là: location nơi lưu tạm file trước khi upload, thường nằm trong /tmp của server tomcat - nhưng ở window có khi bị lỗi ở thư mục này -> dùng /
        // kích thước tối đa của file cho phép
        // kích thước tối đa của 1 request
        // ngưỡng 0
        );
        // cần tạo thêm Bean về cloudinary bên webAppcontextConfigs
    }
    
}
