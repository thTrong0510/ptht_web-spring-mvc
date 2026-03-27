/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.nvtt.springdemo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author vthan
 */
public class SpringDemo {

    public static void main(String[] args) {
        // nếu tạo và sử dụng 1 cách bth chứ ko dùng DJ 
        Khoa khoa = new Khoa();
        khoa.setTen("CNTT");
        
        SinhVien sv = new SinhVien();
        sv.setId(1);
        sv.setTen("Trong");
        sv.setKhoa(khoa);
        // in ra => done 
        
        
        // cách khác dùng rổ đậu tạo IOC Container để chứa -> ko cần tự tạo nữa mà lên rổ đậu lấy về 
        // có nhiều cách tạo XML, Annotation

        // rổ đậu phải được đặt trong classpath 
        // mà trong thư mục resources src: mọi thứ build trong này đều được đẩy vào classpath đường dẫn gốc luôn 
        // => để xml ở trong này 
        
        // => vào Files => src/main tạo folder resources => thì qua bên project sẽ thấy folder other sources 
        // mình sẽ tạo file xml ở trong này về tên file đối với console thì đặt tên gì cũng được vì mình sẽ tự chỉ định file 
        // nhưng khi vào web phải đặt application-context.xml 
        
        // khi tạo cần add thêm package context để kích hoạt cái annotation 
        
        // lúc tạo ra thì nó sẽ là 1 rổ đậu chứa nhiều hạt đậu mỗi hạt đậu đại diện cho 1 đối tượngg trên hệ thống 
        
        // sau khi đã tạo 2 đậu khoa và sva bên rỗ đậu -> thực hiện DI ra đây 
        // => ko được tự tạo đối tượng nữa mà lấy từ context ra 
        
        // nạp rỗ đậu lên - chỉ nạp 1 lần khi chạy project bên web mình sẽ cấu hình cho nó tự động
        // bên console thì phải tự làm ClassPathXmlApplicationContext -> giúp vào classpath nạp file mình lên
        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml"); // chỗ này nếu ko nạp lên được thì khả năng file chưa được nạp vào classpath
        SinhVien sva = (SinhVien) ctx.getBean("sva"); // Phải ép kiểu vì nó trả về kiểu Object hoặc chung chung
        // đây chính là DI lôi 1 đối tượng từ rỗ về - setter DI - trên web nó sẽ tự động 
        SinhVien sva1 = (SinhVien) ctx.getBean("sva");
        
        // svav, sva1 là cùng 1 đối tượng vì Bean mặc định theo kiểu singleton -> khi thay đổi dữ liệu 1 trong 2 thì cả 2 sẽ thay đổi
        // để chỉnh sửa cho nó ko singleton nữa thì vào xml chỉnh thuộc tính scope của Bean đó lại 
        
        // có thể chỉnh dùng contructor injection chứ ko chỉ mỗi setter
        
        // dùng dạng collection trong xml 
        
        // 
        SinhVien svNoKhoa = (SinhVien) ctx.getBean("sv_no_khoa");
        System.err.printf("%d - %s - %s", svNoKhoa.getId(), svNoKhoa.getTen(), svNoKhoa.getKhoa());
    }
}
