/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nvtt.repositories.impl;

import com.nvtt.hibernate.HibernateUtils;
import com.nvtt.hibernate.pojo.User;
import jakarta.persistence.Query;
import org.hibernate.Session;

/**
 *
 * @author huu-thanhduong
 */
public class UserRepositoryImpl {
    public User getUserByUsername(String username) {
        try (Session s = HibernateUtils.getFactory().openSession()) {
            
            // đây là câu query đã được tạo sẵn khi generate class từ db
            Query query = s.createNamedQuery("User.findByUsername", User.class);
            query.setParameter("username", username);
            
            return (User) query.getSingleResult();
        }
    }
}
