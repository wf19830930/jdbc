<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.*"%>
<%@ page import="com.dbhelper.*"%>
<%@page import="java.sql.*, com.mysql.jdbc.Driver"%>  
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>测试</title>
</head>
<body>
	<%
	
		mysqlhelper sqlhp = new mysqlhelper();
		String sql="insert into adm_user  (adm_name,adm_age,adm_birth)values(?,?,?)";
		String age="33";
		String birth="1983-09-30";
		String name="kk";
		List<Object> ls=new LinkedList<Object>();
		ls.add(name);
		ls.add(age);
		ls.add(birth);
		int idx=sqlhp.execute(sql, ls);
		System.out.println(idx);
	
		/**
		
		 //com.mysql.jdbc.Driver  
	    Class.forName(Driver.class.getName()).newInstance();  
	    String url = "jdbc:mysql://192.168.1.10:3306/wf?useUnicode=true&characterEncoding=UTF8";  
	    String user = "root";  
	    String password = "luke606,123";  
	      
	    Connection conn = DriverManager.getConnection(url, user, password);  
	    Statement stmt = conn.createStatement();  
	      
	    String sql = "select * from adm_user";  
	    ResultSet rs = stmt.executeQuery(sql);  
	      
	    while(rs.next()) {  
	        out.print("<br />" + "====================" + "<br />");  
	        out.print(rs.getLong("adm_id") + "   ");  
	        out.print(rs.getString("adm_name") + "   ");  
	        out.print(rs.getString("adm_age") + "   ");  
	      
	    }  **/	
	%>


</body>
</html>