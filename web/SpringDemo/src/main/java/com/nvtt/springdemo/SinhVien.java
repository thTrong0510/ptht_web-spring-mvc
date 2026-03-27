/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nvtt.springdemo;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 *
 * @author vthan
 */
public class SinhVien {
    private int id;
    private String ten;
    
    @Autowired // mặc định khi tạo biến SinhVien sẽ lên rổ đậu tìm Khoa để gán vào
    @Qualifier("khoa1") // nếu có nhiều khoa thì dùng annotation này để chỉ định 
    private Khoa khoa;
    private List<Double> diem;

    public SinhVien() {
    }

    public SinhVien(int id, String ten, Khoa khoa) {
        this.id = id;
        this.ten = ten;
        this.khoa = khoa;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the ten
     */
    public String getTen() {
        return ten;
    }

    /**
     * @param ten the ten to set
     */
    public void setTen(String ten) {
        this.ten = ten;
    }

    /**
     * @return the khoa
     */
    public Khoa getKhoa() {
        return khoa;
    }

    /**
     * @param khoa the khoa to set
     */
    public void setKhoa(Khoa khoa) {
        this.khoa = khoa;
    }

    public void setDiem(List<Double> diem) {
        this.diem = diem;
    }

    public List<Double> getDiem() {
        return diem;
    }
    
    

    @Override
    public String toString() {
        return String.format("%d - %s", this.id, this.ten); 
    }
    
}
