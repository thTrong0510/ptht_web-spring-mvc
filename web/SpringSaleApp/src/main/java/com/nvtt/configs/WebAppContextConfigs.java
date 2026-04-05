/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nvtt.configs;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nvtt.formatters.CategoryFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author vthan
 */
@Configuration
@ComponentScan(
        basePackages = {
            "com.nvtt.controllers",
            "com.nvtt.repositories",
            "com.nvtt.services"
        }
)
@EnableWebMvc
@EnableTransactionManagement
public class WebAppContextConfigs implements WebMvcConfigurer {

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    // thêm formatter để nhận dữ liệu khóa ngoại từ view vào controller
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new CategoryFormatter());
    }

    // cấu hình folder public ra bên ngoài để lưu trữ css, ảnh, ... 
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // chỉ định đường dẫn public ra bên ngoài - tên alias ra ngoài - đường dẫn thực sự 
        // classpath: khi build dự án -> target/classes/static... đât là đường vẫn lý thực sự trên máy tính lưu trữ 
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/images");
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css");

    }

    // bean cloudinary
    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary = new Cloudinary(
                ObjectUtils.asMap(
                    "cloud_name", "web_springmvc",
                    "api_key", "529253433446669",
                    "api_secret", "_4PQw4A1aNKBkaX_gs2gfHviccA",
                    "secure", true)
        );
        return cloudinary;
    }
    
    // bean parser dùng để trích xuất thông tin file ra từ request - phải đặt tên hàm đúng multipartResolver thì nó mới hoạt động
    // hoặc là đặt tên hàm khác nhưng phải cấu hình đây là Bean multipartResolver -> tên hàm chính là tên Bean -> dùng @Bean(name = "multipartResolver")
    @Bean
    public StandardServletMultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
        // tiếp theo cần formatter để trích xuất khóa ngoại ra cũng chính là CategoryFormatter  
    }
    
    
    
}
