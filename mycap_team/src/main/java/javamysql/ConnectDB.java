/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javamysql;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author ChoiSiChang
 */
public class ConnectDB {
    private static ConnectDB instance = new ConnectDB();
    
    public static ConnectDB getInstance() {
		return instance;
	}
    public ConnectDB() {

	}
    
String jdbcUrl = "jdbc:mysql://34.64.100.137:3306/mytestlogin?serverTimezone=Asia/Seoul"; 
String dbId = "root"; // MySQL Í≥ÑÏ†ï
String dbPw = "choi123"; // ÎπÑÎ?Î≤àÌò∏
Connection conn = null;
PreparedStatement pstmt = null;
PreparedStatement pstmt2 = null;
ResultSet rs = null;
ResultSetMetaData rsmd = null;
String sql = "";
String sql2 = "";
String sql3 = "";
String returns ;
String returns2 ; 
String returns3;
ArrayList<String> titleList;
ArrayList<String> dateList;
ArrayList<String> contextList;

public String joindb(String id, String pwd, String name, String tel, String birth) {
    try{
        System.out.println("?Öå?ä§?ä∏1");
        Class.forName("com.mysql.cj.jdbc.Driver"); // JDBC ?ìú?ùº?ù¥Î≤? ?†Å?û¨
        conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw); //  jdbcUrl, dbId,  dbPwÍ∞? ?ì§?ñ¥Í∞?Î©¥Îê® db?ó∞Í≤?
        sql = "select id from login where id = ?";// Ï°∞Ìöå 
        pstmt = conn.prepareStatement(sql);  //preparedstatement?óê sqlÎ¨? Îß§Í∞úÎ≥??àòÎ°? ?Ñ£?ùå
        pstmt.setString(1, id); // ? ?åå?ùºÎØ∏ÌÑ∞ ?Ñ§?†ï
        rs = pstmt.executeQuery(); //ÏøºÎ¶¨Î¨? ?ã§?ñâ 
        if(rs.next()){
            if (rs.getString("id").equals(id)) { // ?ù¥ÎØ? ?ïÑ?ù¥?îîÍ∞? ?ûà?äî Í≤ΩÏö∞
		returns = "id";
	    } 
        }
        else{
            //Í∞íÏù¥ ?óÜ?ñ¥?Ñú Í∞??ä•?ïú Í≤ΩÏö∞
            sql2 = "insert into login values(?,?,?,?,?)"; // ?ÇΩ?ûÖ
            pstmt2 = conn.prepareStatement(sql2);
            pstmt2.setString(1, id);
            pstmt2.setString(2, pwd);
            pstmt2.setString(3, name);
            pstmt2.setString(4, tel);
            pstmt2.setString(5, birth);
            pstmt2.executeUpdate();
            returns = "ok";
        }
        
    }catch(Exception e){
       e.printStackTrace();
    }
    finally{
        if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
        if (conn != null)try {conn.close();} catch (SQLException ex) {}
	if (pstmt2 != null)try {pstmt2.close();} catch (SQLException ex) {}
	if (rs != null)try {rs.close();} catch (SQLException ex) {}
        System.out.println("?Öå?ä§?ä∏2");
    }
    return returns;
}

public String logindb(String id, String pwd){
    try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw);
        sql = "select id,pwd from login where id = ? and pwd=?";// Ï°∞Ìöå
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id);
	pstmt.setString(2, pwd);
        rs = pstmt.executeQuery();
        if (rs.next()){
            if (rs.getString("id").equals(id) && rs.getString("pwd").equals(pwd)){
                returns2 = "true";// Î°úÍ∑∏?ù∏ Í∞??ä•
            }
            else{
                returns2 = "false"; //Î°úÍ∑∏?ù∏ ?ã§?å®
            }
        } else {
            returns2 = "noId"; // ?ïÑ?ù¥?îî ?òê?äî ÎπÑÎ?Î≤àÌò∏ Ï°¥Ïû¨ X
        }
        
    }catch(Exception e){
      e.getMessage();
      System.out.println("Î¨∏Ïû¨Î∞úÏÉù!");
    } finally {if (rs != null)try {rs.close();} catch (SQLException ex) {}
	if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
	if (conn != null)try {conn.close();} catch (SQLException ex) {}
    }
    return returns2;
}

public String writepost(String id, String date, String title, String context) {
    try{
        System.out.println("?Öå?ä§?ä∏1");
        Class.forName("com.mysql.cj.jdbc.Driver"); // JDBC ?ìú?ùº?ù¥Î≤? ?†Å?û¨
        conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw); //  jdbcUrl, dbId,  dbPwÍ∞? ?ì§?ñ¥Í∞?Î©¥Îê® db?ó∞Í≤?
      
            sql3 = "insert into writepost values(?,?,?,?)"; // ?ÇΩ?ûÖ
            pstmt = conn.prepareStatement(sql3);
            pstmt.setString(1, id);
            pstmt.setString(2, date);
            pstmt.setString(3, title);
            pstmt.setString(4, context);
            pstmt.executeUpdate();
            returns3 = "Save success!";
       
    }catch(Exception e){
       e.printStackTrace();
    }
    finally{
        if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
        if (conn != null)try {conn.close();} catch (SQLException ex) {}
	if (rs != null)try {rs.close();} catch (SQLException ex) {}
        System.out.println("?Öå?ä§?ä∏2");
    }
    return returns3;
}

public String getwritecount() {
    try{
        System.out.println("?Öå?ä§?ä∏1");
        Class.forName("com.mysql.cj.jdbc.Driver"); // JDBC ?ìú?ùº?ù¥Î≤? ?†Å?û¨
        conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw); //  jdbcUrl, dbId,  dbPwÍ∞? ?ì§?ñ¥Í∞?Î©¥Îê® db?ó∞Í≤?
      
            sql3 = "select * from writepost"; // ?Öå?ù¥Î∏îÏóê ?ûà?äî Ïª¨Îüº Í∞??àò
            pstmt = conn.prepareStatement(sql3);
            rs = pstmt.executeQuery();
            rsmd = rs.getMetaData();
            returns3 = String.valueOf(rsmd.getColumnCount());
       
    }catch(Exception e){
       e.printStackTrace();
    }
    finally{
        if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
        if (conn != null)try {conn.close();} catch (SQLException ex) {}
	if (rs != null)try {rs.close();} catch (SQLException ex) {}
        System.out.println("?Öå?ä§?ä∏2");
    }
    return returns3;
}

public String findid(String tel, String birth){
    try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw);
        sql = "select id from login where tel = ? and birth=?";// Ï°∞Ìöå
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, tel);
	pstmt.setString(2, birth);
        rs = pstmt.executeQuery();
        if (rs.next()){
                returns2 = "your ID : "+rs.getString("id");// ?Ñ±Í≥?
            }
            else{
                returns2 = "false"; //?ã§?å®
            }
        
        
    }catch(Exception e){
      e.getMessage();
      System.out.println("Î¨∏Ïû¨Î∞úÏÉù!");
    } finally {if (rs != null)try {rs.close();} catch (SQLException ex) {}
	if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
	if (conn != null)try {conn.close();} catch (SQLException ex) {}
    }
    return returns2;
}

public String findpwd(String id,String tel, String birth){
    try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw);
        sql = "select id,tel,birth from login where id=? and tel = ? and birth=?";// Ï°∞Ìöå
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id);
        pstmt.setString(2, tel);
	pstmt.setString(3, birth);
        rs = pstmt.executeQuery();
        if (rs.next()){
                returns2 = "ok";// ?Ñ±Í≥?      
           
        } else{
                returns2 = "false"; //?ã§?å®
            }
        
    }catch(Exception e){
      e.getMessage();
      System.out.println("Î¨∏Ïû¨Î∞úÏÉù!");
    } finally {if (rs != null)try {rs.close();} catch (SQLException ex) {}
	if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
	if (conn != null)try {conn.close();} catch (SQLException ex) {}
    }
    return returns2;
}

public String changepwd(String id,String pwd){
    try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw);
        sql = "UPDATE login SET pwd=? WHERE id=?";// Ï°∞Ìöå
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, pwd);
        pstmt.setString(2, id);
        pstmt.executeUpdate();
       
        out.print("Successful change!");
        String returns3 = "Successful change!";// ?Ñ±Í≥?

    }catch(Exception e){
      e.getMessage();
      System.out.println("Î¨∏Ïû¨Î∞úÏÉù!");
    } finally {if (rs != null)try {rs.close();} catch (SQLException ex) {}
	if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
	if (conn != null)try {conn.close();} catch (SQLException ex) {}
    }
    return returns3;
}




        
}
