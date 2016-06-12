package com.initData.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class DBUtil {
	public static Connection getSqlConnection() {
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // 加载JDBC驱动
		String dbURL = "jdbc:sqlserver://127.0.0.1:1433; DatabaseName=yf_gdzc_new"; // 连接服务器和数据库test
		String userName = "sa"; // 默认用户名
		String userPwd = "123"; // 密码
		Connection dbConn = null;
		try {
			Class.forName(driverName);
			dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbConn;
	}
	
	public static Connection getOrcaleConnection(){
		String driverName = "oracle.jdbc.driver.OracleDriver"; // 加载JDBC驱动
		String dbURL = "jdbc:oracle:thin:@192.168.1.213:1521:YF03";//连接服务器和数据库test
		String userName = "inver"; // 默认用户名
		String userPwd = "inver"; // 密码
		Connection dbConn = null;
		try {
			Class.forName(driverName);
			dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbConn;
	}
	
	public static Connection getOrcaleConnectionByZhengshi(){
		String driverName = "oracle.jdbc.driver.OracleDriver"; // 加载JDBC驱动
		String dbURL = "jdbc:oracle:thin:@1.2.3.54:1521:BASE";//连接服务器和数据库test
		String userName = "inver"; // 默认用户名
		String userPwd = "zfi023"; // 密码
		Connection dbConn = null;
		try {
			Class.forName(driverName);
			dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbConn;
	}
	
	public static Connection getOrcaleConnection2(){
		String driverName = "oracle.jdbc.driver.OracleDriver"; // 加载JDBC驱动
		String dbURL = "jdbc:oracle:thin:@192.168.1.213:1521:YF03";//连接服务器和数据库test
		String userName = "ustore"; // 默认用户名
		String userPwd = "ustore"; // 密码
		Connection dbConn = null;
		try {
			Class.forName(driverName);
			dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbConn;
	}
	
	public static String  getSequence(){
		 String uuid = UUID.randomUUID().toString();   
		return uuid.replace("-", "");
	}
	/**
	 * 
	 * <p>
	 * 汉字转拼音
	 * </p>
	 *
	 * @param hanzi String 需要转换的字符串
	 * @return String 返回已经转换的字符串
	 */
    public static String converterToSpell(String hanzi){           
		// 创建汉语拼音处理类
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		// 输出设置，大小写，音标方式
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		char [] nameChar  = hanzi.toCharArray(); 
		String pinyingStr = "";
		for(char c : nameChar){
			if(c>128){
				try {
					String[] hanyuPinyinStringArray = PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat);
					if(null==hanyuPinyinStringArray)
						continue;
					pinyingStr+=PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat)[0].charAt(0);
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			}else{
				pinyingStr+=c;
			}
		}
		return pinyingStr.toUpperCase();
    }
	public static void setBaseCode(Connection conn,CodeType type,String code) throws SQLException{
		Statement stmtSql=conn.createStatement();
		ResultSet rsSql= stmtSql.executeQuery("select * from BASE_CODE where TYPE='"+type.getType()+"' and HEAD_CODE='"+type.getHeadCode()+"'"); 
		if(rsSql.next()){
			StringBuffer updateSql=new StringBuffer("update BASE_CODE SET CURRENT_CODE='"+code+"' WHERE");
			updateSql.append(" UUID='"+rsSql.getString("UUID")+"'");
			stmtSql.addBatch(updateSql.toString());
		}else{
			StringBuffer insertSql=new StringBuffer("insert into BASE_CODE(UUID,TYPE,NAME,HEAD_CODE,CURRENT_CODE) VALUES(");
			insertSql.append("'"+getSequence()+"',");
			insertSql.append("'"+type.getType()+"',");
			insertSql.append("'"+type.getName()+"',");
			insertSql.append("'"+type.getHeadCode()+"',");
			insertSql.append("'"+code+"'");
			insertSql.append(")");
			stmtSql.addBatch(insertSql.toString());
		}
		stmtSql.executeBatch();
		stmtSql.close();
	}
}
