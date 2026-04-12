/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nvtt.repositories.impl;

import com.nvtt.pojo.User;
import com.nvtt.repositories.UserRepository;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author huu-thanhduong
 */
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public User getUserByUsername(String username) {
        Session s = factory.getObject().getCurrentSession();

        // đây là câu query đã được tạo sẵn khi generate class từ db
        Query query = s.createNamedQuery("User.findByUsername", User.class);
        query.setParameter("username", username);

        return (User) query.getSingleResult();
    }

    public void addOrUpdateUser(User user) {
        Session s = factory.getObject().getCurrentSession();
        if (user.getId() == null) {
            s.persist(user);
        } else {
            s.merge(user);
        }
    }
}
