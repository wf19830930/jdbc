package com.dbhelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class mysqlhelper {
 
	private static final String dbname = "java:comp/env/jdbc/wf";

	 
	/**
	 * 该语句必须是一个 SQL INSERT、UPDATE 或 DELETE 语句
	 * 
	 * @param sql
	 * @param paramList
	 *            ：参数，与SQL语句中的占位符一一对应
	 * @return
	 * @throws Exception
	 */
	public int execute(String sql, List<Object> paramList) throws Exception {
		if (sql == null || sql.trim().equals("")) {
			// logger.info("parameter is valid!");
		}

		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			conn = this.getConnection();

			pstmt = mysqlhelper.getPreparedStatement(conn, sql);
			setPreparedStatementParam(pstmt, paramList);
			if (pstmt == null) {
				return -1;
			}
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			// logger.info(e.getMessage());
			throw new Exception(e);
		} finally {
			closeStatement(pstmt);
			closeConn(conn);
		}

		return result;
	}
	
	   
    /** 
     * 将查询数据库获得的结果集转换为Map对象 
     * @param sql：查询语句 
     * @param paramList：参数 
     * @return 
     */  
    public List<Map<String, Object>> getQueryList(String sql, List<Object> paramList) throws Exception {  
        if(sql == null || sql.trim().equals("")) {  
            //logger.info("parameter is valid!");  
            return null;  
        }  
  
        Connection conn = null;  
        PreparedStatement pstmt = null;  
        ResultSet rs = null;  
        List<Map<String, Object>> queryList = null;  
        try {  
            conn = this.getConnection(); 
            pstmt =mysqlhelper.getPreparedStatement(conn, sql);
            setPreparedStatementParam(pstmt, paramList);  
            if(pstmt == null) {  
                return null;  
            }  
            rs = getResultSet(pstmt);  
            queryList = getQueryList(rs);  
        } catch (RuntimeException e) {  
            //logger.info(e.getMessage());  
            System.out.println("parameter is valid!");  
            throw new Exception(e);  
        } finally {  
            closeResultSet(rs);  
            closeStatement(pstmt);  
            closeConn(conn);  
        }  
        return queryList;  
    } 
    
    
    /** 
     * 获得数据库连接 
     * @return 
     * @throws Exception 
     */  
    private Connection getConnection() throws Exception {  
        InitialContext cxt = new InitialContext();  
        DataSource ds = (DataSource) cxt.lookup(dbname);  
        if ( ds == null ) {  
           throw new Exception("Data source not found!");  
        }  
          
        return ds.getConnection();  
    }  
    private static PreparedStatement getPreparedStatement(Connection conn, String sql) throws Exception {  
        if(conn == null || sql == null || sql.trim().equals("")) {  
            return null;  
        }  
        PreparedStatement pstmt = conn.prepareStatement(sql.trim());  
        return pstmt;  
    } 
    /** 
     * 获得数据库查询结果集 
     * @param pstmt 
     * @return 
     * @throws Exception 
     */  
    private ResultSet getResultSet(PreparedStatement pstmt) throws Exception {  
        if(pstmt == null) {  
            return null;  
        }  
        ResultSet rs = pstmt.executeQuery();  
        return rs;  
    }  
    
    /** 
     * @param rs 
     * @return 
     * @throws Exception 
     */  
    private List<Map<String, Object>> getQueryList(ResultSet rs) throws Exception {  
        if(rs == null) {  
            return null;  
        }  
        ResultSetMetaData rsMetaData = rs.getMetaData();  
        int columnCount = rsMetaData.getColumnCount();  
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();  
        while (rs.next()) {  
            Map<String, Object> dataMap = new HashMap<String, Object>();  
            for (int i = 0; i < columnCount; i++) {  
                dataMap.put(rsMetaData.getColumnName(i+1), rs.getObject(i+1));  
            }  
            dataList.add(dataMap);  
        }  
        return dataList;  
    }  

	private void setPreparedStatementParam(PreparedStatement pstmt,
			List<Object> paramList) throws Exception {
		if (pstmt == null || paramList == null || paramList.isEmpty()) {
			return;
		}
		DateFormat df = DateFormat.getDateTimeInstance();
		for (int i = 0; i < paramList.size(); i++) {
			if (paramList.get(i) instanceof Integer) {
				int paramValue = ((Integer) paramList.get(i)).intValue();
				pstmt.setInt(i + 1, paramValue);
			} else if (paramList.get(i) instanceof Float) {
				float paramValue = ((Float) paramList.get(i)).floatValue();
				pstmt.setFloat(i + 1, paramValue);
			} else if (paramList.get(i) instanceof Double) {
				double paramValue = ((Double) paramList.get(i)).doubleValue();
				pstmt.setDouble(i + 1, paramValue);
			} else if (paramList.get(i) instanceof Date) {
				pstmt.setString(i + 1, df.format((Date) paramList.get(i)));
			} else if (paramList.get(i) instanceof Long) {
				long paramValue = ((Long) paramList.get(i)).longValue();
				pstmt.setLong(i + 1, paramValue);
			} else if (paramList.get(i) instanceof String) {
				pstmt.setString(i + 1, (String) paramList.get(i));
			}
		}
		return;
	}

	/**
	 * 关闭数据库连接
	 * 
	 * @param conn
	 */
	private void closeConn(Connection conn) {
		if (conn == null) {
			return;
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// logger.info(e.getMessage());
		}
	}

	/**
	 * 关闭
	 * 
	 * @param stmt
	 */
	private void closeStatement(Statement stmt) {
		if (stmt == null) {
			return;
		}
		try {
			stmt.close();
		} catch (SQLException e) {
			// logger.info(e.getMessage());
		}
	}

	/**
	 * 关闭
	 * 
	 * @param rs
	 */
	private void closeResultSet(ResultSet rs) {
		if (rs == null) {
			return;
		}
		try {
			rs.close();
		} catch (SQLException e) {
			// logger.info(e.getMessage());
		}
	}
}
