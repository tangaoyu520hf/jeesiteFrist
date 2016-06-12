package com.initData.com.init.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.initData.util.DBUtil;

/**
 * 初始化商品类别
 * @author 
 *
 */
public class InitData {

	public static void main(String[] args) throws SQLException {
		ConnectionDB sqlConnectionDB = new ConnectionDB(DBUtil.getSqlConnection());
		StringBuffer sql1 = new StringBuffer();
		sql1.append(" select '0'+temp.FLID FLID,temp.FLMC,temp.IS_RECIPIENT,'0'+(case when temp.TOP_FJID is null then temp.FJID else temp.TOP_FJID end )FJID from(");
		sql1.append(" select TF.FLID,TF.FLMC,(select newFLID from FL_TEMP where FL_TEMP.FLID=TF.FJID) 'TOP_FJID',TF.FJID,(case when par.val is NULL then 0 else 1 end) IS_RECIPIENT from T_FLB TF left join Fl_TEMP FT on TF.FLID=FT.FLID");
		sql1.append(" left join parameter_t par on par.moniker='LYD2_FLB' and par.val=TF.FLID WHERE TF.LX!=0 AND TF.FJID!=0 ");
		sql1.append(" ) temp");
		List<Map<String, Object>> excuteQuery = sqlConnectionDB.excuteQuery(sql1.toString(), null);
		sqlConnectionDB.closeAll();
		
		StringBuffer sql = new StringBuffer();
		/*sql.append("insert into MOVE_LOG (UUID, MOVDAT, DEPT, STOCKLOC, MOVTYP, ITMID, MOVQTY, MOVPR, MOVSUM, REFERID, REFERPOS, CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE, RELEA_STORAGE)");
		sql.append("values(?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");*/
		sql.append("insert into STANDARD_TYPE (CODEID, CODE_TYPE, CODE_NAME, DESCRIBE, PARENTID, CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE, DELETE_FLAG, CODE_ORDER, IS_SPECIFICATION, IS_RECIPIENT)");
		sql.append("values(?, ?, ?,?, ?, ?, sysdate,?,sysdate,?, ?, ?, ?)");
		
		Connection orcaleConnection = DBUtil.getOrcaleConnection();
		PreparedStatement createStatement = orcaleConnection.prepareStatement(sql.toString());
		System.out.println(excuteQuery.size());
		for(Map<String, Object> map :excuteQuery){
			createStatement.setObject(1, map.get("FLID"));
			createStatement.setObject(2, map.get("FLID"));
			createStatement.setObject(3, map.get("FLMC"));
			createStatement.setObject(4, "");
			createStatement.setObject(5, map.get("FJID"));
			createStatement.setObject(6,"admin");
			createStatement.setObject(7,"admin");
			createStatement.setObject(8,0);
			createStatement.setObject(9, 1);
			createStatement.setObject(10, 0);
			createStatement.setObject(11, map.get("IS_RECIPIENT"));
			createStatement.addBatch();
		}
/*		createStatement.executeBatch();
		createStatement.clearBatch();*/
		orcaleConnection.close();
	}
}
