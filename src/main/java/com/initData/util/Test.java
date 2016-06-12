package com.initData.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {

	public static void main(String[] args) {
		Connection sqlCon=DBUtil.getSqlConnection();
		Connection orcaleCon=DBUtil.getOrcaleConnection();
		Statement stmtSql = null;
		ResultSet rsSql = null;
		try {
			stmtSql=sqlCon.createStatement();
			rsSql= stmtSql.executeQuery("select * from T_GYXX"); 
			setSupplierInfo(rsSql, orcaleCon);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				orcaleCon.setAutoCommit(true);
				orcaleCon.close(); 
				rsSql.close();
				stmtSql.close();
				sqlCon.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} 
	}
	/**
	 * 插入供应商基本信息与联系人信息
	 * @param set
	 * @param conn
	 */
	public static void setSupplierInfo(ResultSet set,Connection conn){
		try {
			Long start = System.currentTimeMillis(); 
			conn.setAutoCommit(false);
			int i=0;
			int j=0;
			Statement stmt = conn.createStatement(); 
			CodeType sType=CodeType.GY01;
			CodeType cType=CodeType.GY04;
			String sMaxCode=null;
			String cMaxCode=null;
			while (set.next()) {
				i++;
				String sCode=sType.getHeadCode()+String.format("%05d", i);
				StringBuffer supplierSql=new StringBuffer("insert into SUPPLIER_INFO(SUPPID,SUPPTYP,NAM1,NAM2,PAYTYP,PAYERTYP,DELETE_FLAG) VALUES (");
				supplierSql.append("'"+sCode+"',");
				supplierSql.append("'GY01',");
				supplierSql.append("'"+set.getString("GYMC")+"',");
				supplierSql.append("'"+set.getString("GYJC")==null?"":set.getString("GYJC")+"',");
				supplierSql.append("'001',");
				supplierSql.append("'01',");
				supplierSql.append("0");
				supplierSql.append(")");
				stmt.addBatch(supplierSql.toString());
				sMaxCode=sCode;
				String lxr=set.getString("LXR");
				if(lxr!=null&&!lxr.equals("NULL")&&!lxr.equals("null")&&!"".equals(lxr.trim())){
					j++;
					String contId=cType.getHeadCode()+String.format("%05d", j);
					StringBuffer supplierLinkSql=new StringBuffer("insert into SUPPLIER_LINK(UUID,SUPPID,CONTID,CONTTYP,CONTNAM,TEL1,ADDR) VALUES (");
					supplierLinkSql.append("'"+DBUtil.getSequence()+"',");
					supplierLinkSql.append("'"+sCode+"',");
					supplierLinkSql.append("'"+contId+"',");
					supplierLinkSql.append("'01',");
					if(lxr.indexOf(" ")>0){
						supplierLinkSql.append("'"+lxr.substring(0, lxr.indexOf(" "))+"',");
					}else{
						supplierLinkSql.append("'"+lxr+"',");
					}
					supplierLinkSql.append("'"+set.getString("DH")==null?"":set.getString("DH")+"',");
					supplierLinkSql.append("'"+set.getString("DZ")==null?"":set.getString("DZ")+"'");
					supplierLinkSql.append(")");
					stmt.addBatch(supplierLinkSql.toString());
					cMaxCode=contId;
				}
			}
			if(sMaxCode!=null){
				DBUtil.setBaseCode(conn, sType, sMaxCode);
			}
			if(sMaxCode!=null){
				DBUtil.setBaseCode(conn, cType, cMaxCode);
			}
			stmt.executeBatch();    //执行批处理 
			conn.commit();
			stmt.close();
			Long end = System.currentTimeMillis(); 
            System.out.println("执行供应商插入操作"+i+"条，供应商联系人插入 "+j+" 条，共耗时：" + (end - start) / 1000f + "秒！"); 
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}


}
