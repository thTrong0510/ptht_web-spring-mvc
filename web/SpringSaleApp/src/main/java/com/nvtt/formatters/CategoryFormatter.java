/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nvtt.formatters;

import com.nvtt.pojo.Category;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author vthan
 */
public class CategoryFormatter implements Formatter<Category>{

    // 2 method này đứng trung gian ở giữa của trước khi đến view và controller 
    
    // gọi khi chuyển thừ controller ra view 
    @Override
    public String print(Category cate, Locale locale) {
        return String.valueOf(cate.getId());
    }

    // từ view đẩy về controller 
    // từ nay khi 1 instant của cate từ view vào nó sẽ đi qua đây để xử lý 
    @Override
    public Category parse(String cateId, Locale locale) throws ParseException {
        Category cate = new Category();
        cate.setId(Integer.valueOf(cateId));
        
        return cate;
    }
    
    // cuối cùng cấu hình vào rổ đậu chính WebAppContextConfigs
}
