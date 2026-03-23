/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.tienquynhtrong.storeproceduredemo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 *
 * @author vthan
 */
public class StoreProcedureDemo {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/saledb", "root", "Thanhtrong@0510")) {
//            String kw = "iphone 7";
//            CallableStatement stm = conn.prepareCall("{CALL get_products(?)}");
//            stm.setString(1, kw);
//            
//            ResultSet rs = stm.executeQuery(); // cursor -> move
//            
//            while(rs.next()) {
//                int id = rs.getInt("id"); // column name in db
//                String name = rs.getString("name");
//                
//                System.out.printf("%d - %s\n", id, name);
//            }
            int id = 1;
            CallableStatement stm = conn.prepareCall("{CALL count_product_by_cate(?, ?)}");
            stm.setInt(1, id);
            stm.registerOutParameter(2, Types.INTEGER);
            
            stm.execute();
            
            System.out.print(stm.getInt(2));
            
            
        }
    }
}
