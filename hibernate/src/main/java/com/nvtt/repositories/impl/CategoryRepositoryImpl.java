/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nvtt.repositories.impl;

import com.nvtt.hibernate.HibernateUtils;
import com.nvtt.hibernate.pojo.Category;
import jakarta.persistence.Query;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author vthan
 */
public class CategoryRepositoryImpl {
    public List<Category> getCates() {
        try(Session session = HibernateUtils.getFactory().openSession()) {
            Query query = session.createQuery("From Category", Category.class);
            query.getResultList();
        }
        return null;
    }
}
