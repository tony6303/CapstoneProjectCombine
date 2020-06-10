<%-- 
    Document   : index
    Created on : 2020. 4. 16, 오전 10:30:18
    Author     : ChoiSiChang
--%>

<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page errorPage="error_page.jsp"%>
<%@ page import="javamysql.ConnectDB" %>
<% // 클래스 임포트 %>
<%
	request.setCharacterEncoding("UTF-8");
	String id = request.getParameter("id");
	String pwd = request.getParameter("pwd");
        
        String name = request.getParameter("name");
        String tel = request.getParameter("tel");
        String birth = request.getParameter("birth");
        
        String date= request.getParameter("date");
        String title= request.getParameter("title");
        String context= request.getParameter("context");
        String changepwd =request.getParameter("changepwd");
        
        ArrayList<String> getTitle = new ArrayList<>();
        
        String type = request.getParameter("type");//로그인 요청인지 회원가입 요청인지를 구분하여 메서드를 실행
        
       
        ConnectDB connectDB = ConnectDB.getInstance();
        if(type.equals("login")) {
		String returns = connectDB.logindb(id, pwd);
		out.print(returns);
	} else if(type.equals("join")) {
		String returns = connectDB.joindb(id, pwd, name, tel, birth);
		out.print(returns);
	}else if(type.equals("writepost")) {
		String returns = connectDB.writepost(id, date, title, context);
		out.print(returns);
	}else if(type.equals("count")) {
		String returns = connectDB.getwritecount();
		out.print(returns);
	}else if(type.equals("findid")) {
		String returns = connectDB.findid(tel, birth);
		out.print(returns);
	}else if(type.equals("findpwd")) {
		String returns = connectDB.findpwd(id ,tel, birth);
		out.print(returns);
	}else if(type.equals("changepwd")) {
		String returns = connectDB.changepwd(id ,pwd);
		out.print(returns);
	}
        
        
%>