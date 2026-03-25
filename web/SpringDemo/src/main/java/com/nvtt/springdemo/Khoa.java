/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nvtt.springdemo;

/**
 *
 * @author vthan
 */
public class Khoa {
    private String ten;

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
    
    // phải có constructor ko tham số vì đa số khi sử dụng setter injection -> thì sẽ vào tìm constructor này 
    // sẽ tạo đối tượng rỗng sau đó vào setter method để set gía trị

    public Khoa() {
    }

    public Khoa(String ten) {
        this.ten = ten;
    }

    @Override
    public String toString() {
        return this.ten;
    }
}
