package com.Struts2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.dbhelper.*;

public class welcome {
	
	private String username;
	private String password;
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String gePassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String sss() throws Exception{
		mysqlhelper msql = new mysqlhelper();
		String sql = "SELECT * FROM ADM_USER WHERE ADM_NAME=? AND ADM_AGE=?";

		String name = this.username;
		String age = this.password;
		List<Object> ls = new LinkedList<Object>();
		ls.add(name);
		ls.add(age);

		ArrayList<Map<String, Object>> lk = (ArrayList<Map<String, Object>>) msql
				.getQueryList(sql, ls);

		if (lk.size() > 0) {
			// TODO Auto-generated method stub
			return "ok";
		}
		else{
			return "no";
		}
	}

}
