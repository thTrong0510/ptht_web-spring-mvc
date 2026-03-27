/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nvtt.configs;

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
            HibernateConfigs.class
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
    
}
